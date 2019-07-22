package bg.jug.mojo.repo;

import java.net.URL;
import java.util.Collections;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.*;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Proxy;
import org.apache.maven.settings.Settings;
import org.apache.maven.wagon.proxy.ProxyInfo;
import org.apache.maven.wagon.proxy.ProxyUtils;
import org.codehaus.plexus.util.StringUtils;

abstract class HttpClientAbstractMojo extends AbstractMojo {

    private static final int DEFAULT_TIMEOUT = 2000;

    @Parameter(defaultValue = "${settings}", readonly = true, required = true)
    private Settings settings;

    /**
     * Creates a new {@code HttpClient} instance.
     *
     * @param url The {@code URL} to use for setting up the client or {@code null}.
     * @return A new {@code HttpClient} instance.
     * @see #DEFAULT_TIMEOUT
     * @since 2.8
     */
    CloseableHttpClient createHttpClient(URL url)
    {
        HttpClientBuilder builder = HttpClients.custom();

        Registry<ConnectionSocketFactory> csfRegistry =
            RegistryBuilder.<ConnectionSocketFactory>create()
                .register( "http", PlainConnectionSocketFactory.getSocketFactory() )
                .register( "https", SSLConnectionSocketFactory.getSystemSocketFactory() )
                .build();

        builder.setConnectionManager( new PoolingHttpClientConnectionManager( csfRegistry ) );
        builder.setDefaultRequestConfig( RequestConfig.custom()
                                                      .setSocketTimeout( DEFAULT_TIMEOUT )
                                                      .setConnectTimeout( DEFAULT_TIMEOUT )
                                                      .setCircularRedirectsAllowed( true )
                                                      .setCookieSpec( CookieSpecs.IGNORE_COOKIES )
                                                      .build() );

        // Some web servers don't allow the default user-agent sent by httpClient
        builder.setUserAgent( "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)" );

        // Some server reject requests that do not have an Accept header
        builder.setDefaultHeaders(Collections.singletonList(new BasicHeader(HttpHeaders.ACCEPT, "*/*")));

        if ( settings != null && settings.getActiveProxy() != null )
        {
            Proxy activeProxy = settings.getActiveProxy();

            ProxyInfo proxyInfo = new ProxyInfo();
            proxyInfo.setNonProxyHosts( activeProxy.getNonProxyHosts() );

            if ( StringUtils.isNotEmpty( activeProxy.getHost() )
                && ( url == null || !ProxyUtils.validateNonProxyHosts( proxyInfo, url.getHost() ) ) )
            {
                HttpHost proxy = new HttpHost( activeProxy.getHost(), activeProxy.getPort() );
                builder.setProxy( proxy );

                if ( StringUtils.isNotEmpty( activeProxy.getUsername() ) && activeProxy.getPassword() != null )
                {
                    Credentials credentials =
                        new UsernamePasswordCredentials( activeProxy.getUsername(), activeProxy.getPassword() );

                    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    credentialsProvider.setCredentials( AuthScope.ANY, credentials );
                    builder.setDefaultCredentialsProvider( credentialsProvider );
                }
            }
        }
        return builder.build();
    }
}
