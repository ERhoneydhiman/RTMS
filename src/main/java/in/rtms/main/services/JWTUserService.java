package in.rtms.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.rtms.main.dao.UserDao;
import in.rtms.main.entities.UserEntity;

@Service
public class JWTUserService implements UserDetailsService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userDao.userByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName());
		
		return User
				.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(authority)
				.build();
	}
	
	
	 public boolean canModifyUser(String username) {
	        // Fetch the logged-in user’s details
	        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String loggedInUsername = authentication.getName();
	        boolean isSuperAdmin = authentication.getAuthorities()
	                                             .stream()
	                                             .anyMatch(role -> role.getAuthority().equals("ROLE_SUPER_ADMIN"));

	        
	        return isSuperAdmin || loggedInUsername.equals(username);
	    }

}
