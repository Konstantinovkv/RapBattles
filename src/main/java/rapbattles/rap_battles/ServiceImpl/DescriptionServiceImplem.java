package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.Models.DAO.DescriptionDAOImplem;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Service.DescriptionService;

@Repository
public class DescriptionServiceImplem implements DescriptionService {

    @Autowired
    DescriptionDAOImplem ddao;

    @Override
    public void updateUserDescription(String description, UserDTO userDTO) {
        if(ddao.findDescriptionById(userDTO.getUser_ID())==null){
            ddao.addDescription(description,userDTO.getUser_ID());
        }
        else {
            ddao.updateDescription(description, userDTO.getUser_ID());
        }
    }
}
