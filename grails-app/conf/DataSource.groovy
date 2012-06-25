hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            pooled = true
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE"
        }
    }
    test {
        dataSource {
            //driverClassName = 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
            driverClassName = 'net.sourceforge.jtds.jdbc.Driver'
            url = 'jdbc:sqlserver://10.149.64.14:1433;databaseName=RPPAScanner_Test;sendStringParametersAsUnicode=false'
            username = 'rppa'
            password = 'password55555'
            dbCreate = 'create'
            pooled = true
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="SELECT 1"
            }
        }
    }
    migrate {
        dataSource {
            driverClassName = 'net.sourceforge.jtds.jdbc.Driver'
            url = 'jdbc:sqlserver://10.149.64.14:1433;databaseName=RPPAScanner_Copy;sendStringParametersAsUnicode=false'
            username = 'rppa'
            password = 'password55555'
            pooled = true
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="SELECT 1"
            }
        }
    }
    production {
        dataSource {
            //driverClassName = 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
            driverClassName = 'net.sourceforge.jtds.jdbc.Driver'
            url = 'jdbc:sqlserver://10.149.64.14:1433;databaseName=RPPAScanner'
            username = 'rppa'
            password = 'password55555'
            dbCreate = 'update'
            pooled = true
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }
    }
}
