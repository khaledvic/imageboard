package com.mycompany.imageboard.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.imageboard.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.Post.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.Post.class.getName() + ".upvoters", jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.Post.class.getName() + ".downvoters", jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.Post.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.Comment.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.Comment.class.getName() + ".upvoters", jcacheConfiguration);
            cm.createCache(com.mycompany.imageboard.domain.Comment.class.getName() + ".downvoters", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
