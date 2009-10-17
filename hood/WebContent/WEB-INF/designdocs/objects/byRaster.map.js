function(doc)
{
    if (doc.loc)
    {
        var lat = doc.loc[0];
        var lon = doc.loc[1];
        emit( [Math.floor(lat * 4) * 2048 + Math.floor(lon * 4), doc.docType], 1);
    }
}