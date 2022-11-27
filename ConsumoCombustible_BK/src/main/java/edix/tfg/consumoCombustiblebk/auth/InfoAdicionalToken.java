package edix.tfg.consumoCombustiblebk.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer{
	
	@Autowired
	private IUsuarioService ius;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Usuario usuario = ius.findByUsername(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("info_adicional", "Hola que tal!: ".concat(authentication.getName()));
		
		info.put("nombre", usuario.getUsuarioNombre());
		info.put("apellido", usuario.getUsuarioApellido1());
		info.put("email", usuario.getUsuarioEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}