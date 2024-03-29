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

/**
 * Creates a project global JSON config.
 * 
 * @author shelmberger
 *
 */
public class JSONConfigFactory
{
    public JSONConfig createJSONConfig()
    {
        JSONParser parser = new JSONParser();

        DefaultTypeConverterRepository typeConverterRepository = new DefaultTypeConverterRepository();
        typeConverterRepository.addTypeConverter(new DateConverter());
        typeConverterRepository.addTypeConverter(new LatLongConverter());
   
        // we use the new sub type matcher  
        ClassNameBasedTypeMapper typeMapper = new ClassNameBasedTypeMapper();
        typeMapper.setBasePackage(AppDocument.class.getPackage().getName());
        // we only want to have AppDocument instances
        typeMapper.setEnforcedBaseType(AppDocument.class);
        // we use the docType property of the AppDocument 
        typeMapper.setDiscriminatorField("docType");
        
        // we only want to do the expensive look ahead if we're being told to
        // deliver AppDocument instances.        
        typeMapper.setPathMatcher(new SubtypeMatcher(AppDocument.class));
        parser.setTypeMapper(typeMapper);
        parser.setTypeConverterRepository(typeConverterRepository);

        JSON json = new JSON();
        json.setTypeConverterRepository(typeConverterRepository);

        return new JSONConfig(json, parser);
    }
}
