package net.edigest.journal.cache;

import net.edigest.journal.entity.ConfigJournalAppEntity;
import net.edigest.journal.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;
    public  Map<String,String>app_cache;

    @PostConstruct
    public void init()
    {
        app_cache=new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity:all)
        {
            app_cache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
