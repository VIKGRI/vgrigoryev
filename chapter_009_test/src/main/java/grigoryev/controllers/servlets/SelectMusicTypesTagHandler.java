package grigoryev.controllers.servlets;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Handler for custom tag. Provides select of music types.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public class SelectMusicTypesTagHandler extends SimpleTagSupport {
    /**
     * Attribute. List of options.
     */
    private List checkBoxList;
    /**
     * Template for inserting check boxes.
     */
    private static final String CHEK_BOX_TEMPLATE
            = " <input type=\"checkbox\" name='%1$s' class=\"musicCheckBoxes\" value='%1$s'> '%1$s'<br>";

    /**
     * Sets list of options.
     * @param checkBoxList list of check boxes
     */
    public void setCheckBoxList(List checkBoxList) {
        this.checkBoxList = checkBoxList;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        for (Object checkBox: this.checkBoxList) {
            String checkBoxTag = String.format(CHEK_BOX_TEMPLATE, (String) checkBox);
            out.println(checkBoxTag);
        }
    }
}