(function($)
{
    
var map ;    
var baseIcon;
var baseURL;
var allMarkers = {};
var timerId;

function getMetaData(name)
{
    return $("meta[name=" + name + "]").attr("content")
}

function onMarkerDelClick(ev)
{
    try
    {
        var href = this.href;
        
        //console.debug("href = %s", href)
        $.ajax({
            url: href,
            dataType: "html",
            success: receiveForm
        });
    }
    catch(e)
    {
        console.error(e);
    }
    return false;
}

function createMarkerForObj(obj)
{
    if (!allMarkers[obj._id])
    {
        var point = new google.maps.LatLng(obj.loc[0], obj.loc[1]);
        
        var typeIconUrls = typeIcons[obj.docType];
        
        if (typeIconUrls)
        {            
            var icon = new google.maps.Icon(baseIcon);
            icon.image = baseURL + typeIconUrls.icon;
            icon.shadow = baseURL + typeIconUrls.shadow;
        }
        else
        {
            icon = G_DEFAULT_ICON;
        }
        
        var marker = new google.maps.Marker(point, {icon: icon});
        map.addOverlay(marker);
        var $wnd = $("<div class=\"infoWnd\"><b>" + obj.name + "</b><br/>" + obj.description + "<p><a class=\"del\" href=\"../app/del?ajax=1&id=" + obj._id + "\">Delete</a></p></div>");
        
        $("a.del", $wnd).click( onMarkerDelClick);
                    
        marker.bindInfoWindow($wnd[0]);

        allMarkers[obj._id] = marker;
    }
}


function receiveObjectsResponse(result)
{
    var rows = result.rows;
    var len = rows.length;
    for (var i=0; i < len; i++)
    {
        var obj = rows[i].doc;
        createMarkerForObj(obj);
    }
}
    
google.load("maps", "2.x");

var typeIcons = {
    "Hood" : { 
        icon: "/image/hood.png",
        shadow: "/image/hood_shadow.png"
    },
    "Person" : { 
        icon: "/image/person.png",
        shadow: "/image/person_shadow.png"
    },
    "Place" : { 
        icon: "/image/place.png",
        shadow: "/image/place_shadow.png"
    }        
};

function filterFn(field)
{        
    return field.name && !field.disabled &&
            (field.checked || /select|textarea/i.test(field.nodeName) ||
                    /text|hidden|password/i.test(field.type)) && !$(field).hasClass("ignored");
};

function getFormContent(form)
{
    var params = {};
    var fields = form.elements;
    
    for (var i=0; i < fields.length; i++)
    {   
        var field = fields[i];
        if (filterFn(field))
        {
            //console.debug("item = %x", field);
            var old = params[ field.name ];
            var value = $(field).val();
            
            if (old)
            {
                if (typeof old == "string")
                {
                    params[ field.name ] = [ old , value ];
                }
                else
                {
                    params[ field.name ].push(value);
                }
            }
            else
            {        
                params[ field.name ]  = value;
            }
        }
    }
    
    return params;
}


function closePopup(ev)
{
    try
    {
        $("div.popup").remove();
    }
    catch(e)
    {
        console.error(e);
    }
    return false;
}

function submitForm(ev)
{
    try
    {
        var $form = ev.data;
        
        var params = getFormContent($form[0]);
        params.ajax = 1;
        params[this.name] = "clicked";
        
        $.ajax({
           url: $form[0].action,
           data: params,
           dataType: "json",
           success: 
                function receiveFormResponse(result)
                {
                    if (result.ok)
                    {
                        if (result.deleted)
                        {
                            var marker = allMarkers[result.deleted];
                            if (marker)
                            {
                                map.removeOverlay(marker);
                                delete allMarkers[result.deleted];
                            }
                        }
                        else
                        {
                            loadObjectsInBounds();
                        }
                        
                        closePopup();
                    }
                    else
                    {
                        var $container = $("#errorContainer", $form).empty();
                        
                        var errors = result.errors;
                        var len = errors.length;
                        
                        var s = "";
                        for (var i=0; i < len; i++)
                        {
                            s += "<div class=\"error\">" + errors[i].defaultMessage + "</div>";
                        }
                        $container.append(s);
                    }
                }
        });
    }
    catch(e)
    {
        console.error(e);
    }
    return false;
}

function receiveForm(html)
{
    var $wnd = $("<div class=\"popup\">" + html + "</div>");
    
    $("#cancel", $wnd).attr("disabled", null).click(closePopup);
    $("#ok", $wnd).bind("click", $("form", $wnd), submitForm);
    
    
    $(document.body).append($wnd);
}

function onDoubleClick(overlay, latlon)
{
    //console.debug("double click at %s, %s", latlon.lat(), latlon.lng());
    
    $.ajax({
        url: baseURL + "/app/new",
        data: {
            ajax: 1,
            lat: latlon.lat(),
            lon: latlon.lng()
        },
        dataType: "html",
        success: receiveForm
    });
}

var throttleLoadObjects = function(ev)
{
    if (timerId)
    {
        clearTimeout(timerId);
    }        
    timerId = setTimeout(loadObjectsInBounds, 750);
};    

function loadObjectsInBounds()
{
    var bounds = map.getBounds();
    var southWest = bounds.getSouthWest();
    var northEast = bounds.getNorthEast();
    
    console.debug("delta lat = %s, delta lon = %s", northEast.lat()-southWest.lat(), northEast.lng()-southWest.lng())
    
    $.ajax({
        url: baseURL + "/app/objects/json",
        data: 
        {
            "ne_lat" : northEast.lat(),
            "ne_lon" : northEast.lng(),
            "sw_lat" : southWest.lat(),
            "sw_lon" : southWest.lng(),
            "over" : 2.5
        },
        dataType: "json",
        success: receiveObjectsResponse
    })
}

// Call this function when the page has been loaded
function initialize() {

    baseURL = getMetaData("base_url");
    baseURL = baseURL.substring(0, baseURL.length - 1);
    
    //console.debug(baseURL);
    
    // Create a base icon for all of our markers that specifies the
    // shadow, icon dimensions, etc.
    baseIcon = new google.maps.Icon(G_DEFAULT_ICON);
    baseIcon.shadow = "http://www.google.com/mapfiles/shadow50.png";
    baseIcon.iconSize = new google.maps.Size(32, 37);
    baseIcon.shadowSize = new google.maps.Size(43, 37);
    baseIcon.iconAnchor = new google.maps.Point(15, 37);
    baseIcon.infoWindowAnchor = new google.maps.Point(15, 2);
    
    var $elem = $("#map").empty();
    
    var hood = JSON.parse(getMetaData("hood_info"));
    map = new google.maps.Map2($elem[0]);
    map.setUIToDefault();
    map.disableDoubleClickZoom();

    var center = new google.maps.LatLng(hood.loc[0],hood.loc[1]);
    map.setCenter(center, 16);
    
    google.maps.Event.addListener( map, "dblclick", onDoubleClick); 
    google.maps.Event.addListener( map, "zoomend", throttleLoadObjects); 
    google.maps.Event.addListener( map, "moveend", throttleLoadObjects); 
    
    if (!hood.defaultHood)
    {
        createMarkerForObj(hood);
    }
    else
    {
        loadObjectsInBounds();
    }
}
google.setOnLoadCallback(initialize);

})(jQuery);

