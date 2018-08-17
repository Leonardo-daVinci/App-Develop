package apps.nocturnal.com.chatsite;

public class Users {

    public String Name,Image,Status;

    public  Users(){

    }

    public Users(String name, String image, String status) {
        Name = name;
        Image = image;
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
