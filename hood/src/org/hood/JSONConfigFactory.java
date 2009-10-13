package org.hood;
import org.hood.domain.AppDocument;
import org.hood.domain.LatLongConverter;
import org.svenson.ClassNameBasedTypeMapper;
import org.svenson.JSON;
import org.svenson.JSONConfig;
import org.svenson.JSONParser;
import org.svenson.converter.DateConverter;
import org.svenson.converter.DefaultTypeConverterRepository;
import org.svenson.matcher.SubtypeMatcher;


public class JSONConfigFactory
{
    public JSONConfig createJSONConfig()
    {
        JSONParser parser = new JSONParser();

        DefaultTypeConverterRepository typeConverterRepository = new DefaultTypeConverterRepository();
        typeConverterRepository.addTypeConverter(new DateConverter());
        typeConverterRepository.addTypeConverter(new LatLongConverter());
        
        ClassNameBasedTypeMapper typeMapper = new ClassNameBasedTypeMapper();
        typeMapper.setBasePackage(AppDocument.class.getPackage().getName());
        typeMapper.setEnforcedBaseType(AppDocument.class);
        typeMapper.setDiscriminatorField("docType");
        typeMapper.setPathMatcher(new SubtypeMatcher(AppDocument.class));
        parser.setTypeMapper(typeMapper);
        parser.setTypeConverterRepository(typeConverterRepository);

        JSON json = new JSON();
        json.setTypeConverterRepository(typeConverterRepository);

        return new JSONConfig(json, parser);
    }
}
