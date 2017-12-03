package grigoryev.models;

/**
 * Music type model in the database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public class MusicType extends  Model {

    private static final long serialVersionUID = 1850875363862010847L;
    /**
     * Music type title.
     */
    private String title;
    /**
     * Music type description.
     */
    private String description;

    /**
     * Constructor.
     */
    public MusicType() {
    }

    /**
     * Constructor.
     * @param id id
     */
    public MusicType(Integer id) {
        super(id);
    }

    /**
     * Gets title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
