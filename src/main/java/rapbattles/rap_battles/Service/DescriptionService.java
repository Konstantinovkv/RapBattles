package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.DTO.UserDTO;

public interface DescriptionService {

    void updateUserDescription(String description, UserDTO userDTO);
}
