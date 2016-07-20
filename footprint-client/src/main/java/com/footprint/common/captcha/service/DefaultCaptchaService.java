package com.footprint.common.captcha.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import com.footprint.common.captcha.background.BackgroundFactory;
import com.footprint.common.captcha.background.SingleColorBackgroundFactory;
import com.footprint.common.captcha.color.ColorFactory;
import com.footprint.common.captcha.color.GradientColorFactory;
import com.footprint.common.captcha.color.SingleColorFactory;
import com.footprint.common.captcha.filter.FilterFactory;
import com.footprint.common.captcha.filter.predefined.CurvesRippleFilterFactory;
import com.footprint.common.captcha.font.FontFactory;
import com.footprint.common.captcha.font.RandomFontFactory;
import com.footprint.common.captcha.font.UpperRandomWordFactory;
import com.footprint.common.captcha.text.TextRenderer;
import com.footprint.common.captcha.text.renderer.BestFitTextRenderer;
import com.footprint.common.captcha.word.AdaptiveRandomWordFactory;
import com.footprint.common.captcha.word.WordFactory;

/**
 * 验证码服务类
 */
public class DefaultCaptchaService implements CaptchaService {

	protected FontFactory fontFactory;

	protected WordFactory wordFactory;

	protected ColorFactory colorFactory;

	protected BackgroundFactory backgroundFactory;

	protected TextRenderer textRenderer;

	protected FilterFactory filterFactory;

	/** 宽 */
	protected int width;

	/** 高 */
	protected int height;

	public DefaultCaptchaService() {
		backgroundFactory = new SingleColorBackgroundFactory();
		wordFactory = new UpperRandomWordFactory();
		fontFactory = new RandomFontFactory();
		textRenderer = new BestFitTextRenderer();
		colorFactory = new GradientColorFactory();
		filterFactory = new CurvesRippleFilterFactory(colorFactory);
		textRenderer.setLeftMargin(5);
		textRenderer.setRightMargin(5);
		width = 140;
		height = 50;
	}
	
	public DefaultCaptchaService(int width, int height) {
		backgroundFactory = new SingleColorBackgroundFactory();
		wordFactory = new UpperRandomWordFactory();
		fontFactory = new RandomFontFactory();
		textRenderer = new BestFitTextRenderer();
		colorFactory = new GradientColorFactory();
		filterFactory = new CurvesRippleFilterFactory(colorFactory);
		textRenderer.setLeftMargin(10);
		textRenderer.setRightMargin(10);
		this.width = width;
		this.height = height;
	}
	
	public DefaultCaptchaService(int width, int height, Color textColor, Color backgroundColor, int fontSize, FilterFactory ff) {
		backgroundFactory = new SingleColorBackgroundFactory(backgroundColor);
		wordFactory = new AdaptiveRandomWordFactory();
		fontFactory = new RandomFontFactory();
		textRenderer = new BestFitTextRenderer();
		colorFactory = new SingleColorFactory(textColor);
		filterFactory = ff;
		this.width = width;
		this.height = height;
	}
	
	public DefaultCaptchaService(int width, int height, Color textColor, Color backgroundColor, int fontSize, String[]fontNames, FilterFactory ff) {
		backgroundFactory = new SingleColorBackgroundFactory(backgroundColor);
		wordFactory = new AdaptiveRandomWordFactory();
		fontFactory = new RandomFontFactory(fontNames);
		textRenderer = new BestFitTextRenderer();
		colorFactory = new SingleColorFactory(textColor);
		filterFactory = ff;
		this.width = width;
		this.height = height;
	}

	public void setFontFactory( FontFactory fontFactory ) {
		this.fontFactory = fontFactory;
	}


	public void setWordFactory( WordFactory wordFactory ) {
		this.wordFactory = wordFactory;
	}


	public void setColorFactory( ColorFactory colorFactory ) {
		this.colorFactory = colorFactory;
	}


	public void setBackgroundFactory( BackgroundFactory backgroundFactory ) {
		this.backgroundFactory = backgroundFactory;
	}


	public void setTextRenderer( TextRenderer textRenderer ) {
		this.textRenderer = textRenderer;
	}


	public void setFilterFactory( FilterFactory filterFactory ) {
		this.filterFactory = filterFactory;
	}


	public FontFactory getFontFactory() {
		return fontFactory;
	}


	public WordFactory getWordFactory() {
		return wordFactory;
	}


	public ColorFactory getColorFactory() {
		return colorFactory;
	}


	public BackgroundFactory getBackgroundFactory() {
		return backgroundFactory;
	}


	public TextRenderer getTextRenderer() {
		return textRenderer;
	}


	public FilterFactory getFilterFactory() {
		return filterFactory;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public void setWidth( int width ) {
		this.width = width;
	}


	public void setHeight( int height ) {
		this.height = height;
	}

	@Override
	public Captcha getCaptcha() {
		BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		backgroundFactory.fillBackground(bufImage);
		String word = wordFactory.getNextWord();
		textRenderer.draw(word, bufImage, fontFactory, colorFactory);
		bufImage = filterFactory.applyFilters(bufImage);
		return new Captcha(word, bufImage);
	}
	
	@Override
	public void saveChallengeToSession(HttpServletRequest request, String value) {
		request.getSession().setAttribute(SESSION_CAPTCHA, value);
	}

	@Override
	public String getChallengeInSession(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(SESSION_CAPTCHA);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		CaptchaService cs = new DefaultCaptchaService();
		Captcha c = cs.getCaptcha();
		System.out.println(c.getChallenge());
		c.writeImage(new FileOutputStream("d://fff.png"));
	}

}
