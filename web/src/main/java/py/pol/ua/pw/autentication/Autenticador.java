package py.pol.ua.pw.autentication;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.security.auth.login.LoginException;

@Local
@Singleton
public class Autenticador {
	private static Autenticador autenticador = null;
	 
   
    private  Map<String, String> authorizationTokensStorage;
    
     public Autenticador(){
    	 authorizationTokensStorage = new HashMap<String, String>();
    }
 
    public String login(String username, String password ) throws LoginException { 
                if ( password.equals( username) ) {

                    String authToken = UUID.randomUUID().toString();
                    authorizationTokensStorage.put( authToken, username );
                    return authToken;
                }
        throw new LoginException( "Don't Come Here Again!" );
    }
    public boolean isAuthTokenValid(String authToken) {
 
            if ( authorizationTokensStorage.containsKey( authToken ) ) {
                return true;
            }
 
        return false;
    }
    public void logout( String authToken ) throws GeneralSecurityException {
   
            if ( authorizationTokensStorage.containsKey( authToken ) ) {
                authorizationTokensStorage.remove( authToken );
            }
        throw new GeneralSecurityException( "Authorization token match." );
    }
 
}
