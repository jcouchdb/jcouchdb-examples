function(doc)
{
    if (doc.loc)
    {
        emit(doc.loc[0],doc.loc[1]);
    }
}