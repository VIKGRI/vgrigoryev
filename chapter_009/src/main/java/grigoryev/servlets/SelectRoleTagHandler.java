package grigoryev.servlets;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Handler for custom tag. Provides select of roles.
 *
 * @author vgrigoryev
 * @version 1
 * @since 17.11.2017
 */
public class SelectRoleTagHandler extends SimpleTagSupport {
    /**
     * Attribute. List of options.
     */
    private List optionList;
    /**
     * Attribute. Name.
     */
    private String name;
    /**
     * Attribute. Size.
     */
    private String size;
    /**
     * Template for constructing attributes.
     */
    private static final String ATTR_TEMPLATE = "%s='%s' ";
    /**
     * Template for inserting options.
     */
    private static final String OPTION_TEMPLATE = " <option value='%1$s'> %1$s </option>";

    /**
     * Sets list of options.
     * @param optionList list of options
     */
    public void setOptionList(List optionList) {
        this.optionList = optionList;
    }

    /**
     * Sets name.
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets size.
     * @param size size
     */
    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        out.print("<select ");
        out.print(String.format(ATTR_TEMPLATE, "name", this.name));
        out.print(String.format(ATTR_TEMPLATE, "size", this.size));
        out.println('>');
        for (Object option: this.optionList) {
            String optionTag = String.format(OPTION_TEMPLATE, (String) option);
            out.println(optionTag);
        }
        out.println("</select>");
    }
}
