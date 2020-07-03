package cn.fllday.learn.auth.config.oauth2;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: gssznb
 */
@Component
public class MapJdbcClientDetailsServiceImpl extends JdbcClientDetailsService {

    private Map<String, ClientDetails> clientDetailsMap = new ConcurrentHashMap<>();

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

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        super.updateClientDetails(clientDetails);
        String clientId = clientDetails.getClientId();
        this.clientDetailsMap.put(clientId, clientDetails);
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        super.addClientDetails(clientDetails);
        clientDetailsMap.put(clientDetails.getClientId(), clientDetails);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        clientDetailsMap.remove(clientId);
        super.removeClientDetails(clientId);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("111111"));
    }
}
