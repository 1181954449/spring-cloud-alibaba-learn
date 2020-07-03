package cn.fllday.learn.auth.config.oauth2;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: gssznb
 */
@Component
public class MapJdbcClientDetailsServiceImpl extends JdbcClientDetailsService {

    private Map<String, ClientDetails> clientDetailsMap = new HashMap<>();

    public MapJdbcClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        if (clientDetailsMap.get(clientId) != null) {
            return clientDetailsMap.get(clientId);
        }
        ClientDetails clientDetails = super.loadClientByClientId(clientId);
        clientDetailsMap.putIfAbsent(clientId, clientDetails);
        return clientDetails;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder e = new BCryptPasswordEncoder();
        System.out.println(e.encode("111222"));
    }
}
