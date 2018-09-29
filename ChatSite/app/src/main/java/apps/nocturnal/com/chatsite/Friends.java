package apps.nocturnal.com.chatsite;

public class Friends {

    public String name,image,friendship,thumb_image;

    public Friends(String name, String image, String friendship, String thumb_image) {
        this.name = name;
        this.image = image;
        this.friendship = friendship;
        this.thumb_image = thumb_image;
    }

    public Friends(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFriendship() {
        return friendship;
    }

    public void setFriendship(String friendship) {
        this.friendship = friendship;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }
}
