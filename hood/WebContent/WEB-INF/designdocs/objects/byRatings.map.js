function(doc)
{
    if (doc.docType == "Rating")
    {
        emit([doc.targetId, doc.created, doc.userId], doc.rating);
    }
}