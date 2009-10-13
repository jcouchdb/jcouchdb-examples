function(doc)
{
    if (doc.docType == "Hood" && doc.defaultHood)
    {
        emit(doc.name, null);
    }
}