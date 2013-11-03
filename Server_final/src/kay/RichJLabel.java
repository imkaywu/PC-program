package kay;

import java.awt.Color;  
import java.awt.Dimension;  
import java.awt.FontMetrics;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.GridLayout;  
import java.awt.RenderingHints;  
import java.awt.Toolkit;  
  
import javax.swing.JDialog;  
import javax.swing.JLabel;  
import javax.swing.JPanel;  
import javax.swing.WindowConstants;  
  
/** 
 * 富标签 
 * @author 翟天龙 
 * @version V1.0 2011-1-21 
 */  
public class RichJLabel extends JLabel  
{  
    /** . */  
    private static final long serialVersionUID = 6726176528338618387L;  
  
    /** 
     * 每个字之间的距离 
     */  
    private int tracking;  
  
    /** 
     * 返回一个简单轮廓样式的JLabel 
     *  
     * @param text 
     *            文字 
     * @param fontSize 
     *            字体大小 
     */  
    public static RichJLabel getOutlineLabel(String text, float fontSize)  
    {  
        return getOutlineLabel(text, 0, Color.WHITE, Color.BLACK, fontSize);  
    }  
  
    /** 
     * 返回一个简单轮廓样式的JLabel 
     *  
     * @param text 
     *            文字 
     * @param tracking 
     *            文字间距 
     * @param fontColor 
     *            字的颜色 
     * @param lineColor 
     *            边框样色 
     * @param fontSize 
     *            字体大小 
     * @return 
     */  
    public static RichJLabel getOutlineLabel(String text, int tracking, Color fontColor, Color lineColor, float fontSize)  
    {  
        RichJLabel label = new RichJLabel(text, tracking, fontSize);  
  
        label.setLeftShadow(1, 1, Color.BLACK);  
        label.setRightShadow(1, 1, Color.BLACK);  
        label.setForeground(Color.WHITE);  
  
        return label;  
    }  
  
    /** 
     * 返回一个阴影遮蔽样式的JLabel 
     *  
     * @param text 
     *            文字 
     * @param fontSize 
     *            字体大小 
     */  
    public static RichJLabel getShadowLabel(String text, float fontSize)  
    {  
        return getShadowLabel(text, 0, Color.WHITE, Color.GRAY, Color.BLACK, fontSize);  
    }  
  
    /** 
     * 返回一个阴影遮蔽样式的JLabel 
     *  
     * @param text 
     *            文字 
     * @param tracking 
     *            字间距 
     * @param fontColor 
     *            字体颜色 
     * @param leftColor 
     *            阴影颜色 
     * @param rightColor 
     *            字体侧边颜色 
     * @param fontSize 
     *            字体大小 
     */  
    public static RichJLabel getShadowLabel(String text, int tracking, Color fontColor, Color leftColor, Color rightColor, float fontSize)  
    {  
        RichJLabel label = new RichJLabel(text, tracking, fontSize);  
  
        label.setLeftShadow(2, 2, leftColor);  
        label.setRightShadow(2, 3, rightColor);  
        label.setForeground(fontColor);  
  
        return label;  
    }  
  
    /** 
     * 返回一个3D样式的JLabel 
     *  
     * @param text 
     *            文字 
     * @param fontSize 
     *            字体大小 
     */  
    public static RichJLabel get3DLabel(String text, float fontSize)  
    {  
        return get3DLabel(text, 0, Color.WHITE, Color.GRAY, fontSize);  
    }  
  
    /** 
     * 返回一个阴影遮蔽样式的JLabel 
     *  
     * @param text 
     *            文字 
     * @param tracking 
     *            字间距 
     * @param fontColor 
     *            字体颜色 
     * @param sideColor 
     *            字体侧边颜色 
     * @param fontSize 
     *            字体大小 
     */  
    public static RichJLabel get3DLabel(String text, int tracking, Color fontColor, Color sideColor, float fontSize)  
    {  
        RichJLabel label = new RichJLabel(text, tracking, fontSize);  
  
        label.setLeftShadow(5, 5, sideColor);  
        label.setRightShadow(-3, -3, sideColor);  
        label.setForeground(fontColor);  
  
        return label;  
    }  
  
    /** 
     * 构造方法 
     *  
     * @param text 
     * @param tracking 
     *            每个字之间的距离 
     */  
    private RichJLabel( String text, int tracking, float fontSize )  
    {  
        super(text);  
        this.tracking = tracking;  
        setFont(getFont().deriveFont(fontSize));  
    }  
  
    private int left_x, left_y, right_x, right_y;  
  
    private Color left_color, right_color;  
  
    public void setLeftShadow(int x, int y, Color color)  
    {  
        left_x = x;  
        left_y = y;  
        left_color = color;  
    }  
  
    public void setRightShadow(int x, int y, Color color)  
    {  
        right_x = x;  
        right_y = y;  
        right_color = color;  
    }  
  
    public Dimension getPreferredSize()  
    {  
        String text = getText();  
        FontMetrics fm = this.getFontMetrics(getFont());  
  
        int w = fm.stringWidth(text);  
        w += (text.length() - 1) * tracking;  
        w += left_x + right_x;  
  
        int h = fm.getHeight();  
        h += left_y + right_y;  
  
        return new Dimension(w, h);  
    }  
  
    public void paintComponent(Graphics g)  
    {  
        // 打开文字抗锯齿  
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  
  
        char[] chars = getText().toCharArray();  
  
        FontMetrics fm = this.getFontMetrics(getFont());  
        int h = fm.getAscent();  
        g.setFont(getFont());  
  
        int x = 0;  
  
        for (int i = 0; i < chars.length; i++)  
        {  
            char ch = chars[i];  
            int w = fm.charWidth(ch) + tracking;  
  
            g.setColor(left_color);  
            g.drawString("" + chars[i], x - left_x, h - left_y);  
  
            g.setColor(right_color);  
            g.drawString("" + chars[i], x + right_x, h + right_y);  
  
            g.setColor(getForeground());  
            g.drawString("" + chars[i], x, h);  
  
            x += w;  
        }  
    }  
  
} 