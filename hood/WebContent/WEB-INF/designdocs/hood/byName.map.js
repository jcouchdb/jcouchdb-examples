function(doc)
{
    if (doc.docType == "Hood")
    {
        emit(doc.name, null);
    }
}