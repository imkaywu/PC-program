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
 * ����ǩ 
 * @author ������ 
 * @version V1.0 2011-1-21 
 */  
public class RichJLabel extends JLabel  
{  
    /** . */  
    private static final long serialVersionUID = 6726176528338618387L;  
  
    /** 
     * ÿ����֮��ľ��� 
     */  
    private int tracking;  
  
    /** 
     * ����һ����������ʽ��JLabel 
     *  
     * @param text 
     *            ���� 
     * @param fontSize 
     *            �����С 
     */  
    public static RichJLabel getOutlineLabel(String text, float fontSize)  
    {  
        return getOutlineLabel(text, 0, Color.WHITE, Color.BLACK, fontSize);  
    }  
  
    /** 
     * ����һ����������ʽ��JLabel 
     *  
     * @param text 
     *            ���� 
     * @param tracking 
     *            ���ּ�� 
     * @param fontColor 
     *            �ֵ���ɫ 
     * @param lineColor 
     *            �߿���ɫ 
     * @param fontSize 
     *            �����С 
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
     * ����һ����Ӱ�ڱ���ʽ��JLabel 
     *  
     * @param text 
     *            ���� 
     * @param fontSize 
     *            �����С 
     */  
    public static RichJLabel getShadowLabel(String text, float fontSize)  
    {  
        return getShadowLabel(text, 0, Color.WHITE, Color.GRAY, Color.BLACK, fontSize);  
    }  
  
    /** 
     * ����һ����Ӱ�ڱ���ʽ��JLabel 
     *  
     * @param text 
     *            ���� 
     * @param tracking 
     *            �ּ�� 
     * @param fontColor 
     *            ������ɫ 
     * @param leftColor 
     *            ��Ӱ��ɫ 
     * @param rightColor 
     *            ��������ɫ 
     * @param fontSize 
     *            �����С 
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
     * ����һ��3D��ʽ��JLabel 
     *  
     * @param text 
     *            ���� 
     * @param fontSize 
     *            �����С 
     */  
    public static RichJLabel get3DLabel(String text, float fontSize)  
    {  
        return get3DLabel(text, 0, Color.WHITE, Color.GRAY, fontSize);  
    }  
  
    /** 
     * ����һ����Ӱ�ڱ���ʽ��JLabel 
     *  
     * @param text 
     *            ���� 
     * @param tracking 
     *            �ּ�� 
     * @param fontColor 
     *            ������ɫ 
     * @param sideColor 
     *            ��������ɫ 
     * @param fontSize 
     *            �����С 
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
     * ���췽�� 
     *  
     * @param text 
     * @param tracking 
     *            ÿ����֮��ľ��� 
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
        // �����ֿ����  
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