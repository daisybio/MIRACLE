hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}

// environment specific settings
if(!dataSource){
    environments {
        development {
            dataSource {
                pooled = true
                driverClassName = "org.h2.Driver"
                username = "sa"
                password = ""
                dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
                url = "jdbc:h2:file:devDb;MVCC=TRUE"
            }
        }
        test {
            dataSource {
                pooled = true
                driverClassName = "org.h2.Driver"
                username = "sa"
                password = ""
                dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
                url = "jdbc:h2:file:testDb;MVCC=TRUE"
            }
        }
        prod {
            dataSource {
                pooled = true
                driverClassName = "org.h2.Driver"
                username = "sa"
                password = ""
                dbCreate = "create" // one of 'create', 'create-drop', 'update', 'validate', ''
                url = "jdbc:h2:file:prodDb;MVCC=TRUE"
            }
        }
    }
}

if(!dataSource_SAVANAH)
{
    dataSource_SAVANAH{
        pooled = true
        driverClassName = "org.h2.Driver"
        username = "sa"
        password = ""
        dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
        url = "jdbc:h2:file:savanahDb;MVCC=TRUE"
    }
}

