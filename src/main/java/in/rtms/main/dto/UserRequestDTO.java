package in.rtms.main.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
	private String username;
	private String firstName;
	private String lastName;
	private Long phoneNumber;
	private String email;
	private String password;

}
