/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2026 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.watabou.noosa;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.watabou.glwrap.Matrix;
import com.watabou.glwrap.Quad;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class RenderedText extends Image {
	
	private BitmapFont font = null;
	private int size;
	private String text;
	private String displayText;
	
	public RenderedText( ) {
		text = null;
	}
	
	public RenderedText( int size ){
		text = null;
		this.size = size;
	}
	
	public RenderedText(String text, int size){
		this.text = text;
		this.size = size;
		
		measure();
	}
	
	public void text( String text ){
		this.text = text;
		
		measure();
	}
	
	public String text(){
		return text;
	}
	
	public void size( int size ){
		this.size = size;
		measure();
	}

	private static final ArrayList<Character> alreadyReported = new ArrayList<>();
	
	private synchronized void measure(){
		
		if (Thread.currentThread().getName().equals("SHPD Actor Thread")){
			throw new RuntimeException("Text measured from the actor thread!");
		}
		
		if (text == null || text.equals("")) {
			text = "";
			displayText = "";
			width = height = 0;
			visible = false;
			return;
		} else {
			visible = true;
		}

		displayText = toDisplayText(text);

		font = Game.platform.getFont(size, displayText, true, true);
		
		if (font != null){
						GlyphLayout glyphs = new GlyphLayout( font, displayText);
			
			for (char c : displayText.toCharArray()) {
				BitmapFont.Glyph g = font.getData().getGlyph(c);
				if (g == null || (g.id != c)){
					String toException = displayText;
					if (toException.length() > 30){
						toException = toException.substring(0, 30) + "...";
					}
					//reduces logspam
					if (!alreadyReported.contains(c)) {
						Game.reportException(new Throwable("font file " + font.toString() + " could not render " + c + " from string: " + toException));
						alreadyReported.add(c);
					}
				}
			}
			
			//We use the xadvance of the last glyph in some cases to fix issues
			// with fullwidth punctuation marks in some asian scripts
			BitmapFont.Glyph lastGlyph = font.getData().getGlyph(displayText.charAt(displayText.length()-1));
			if (lastGlyph != null && lastGlyph.xadvance > lastGlyph.width*1.5f){
				width = glyphs.width - lastGlyph.width + lastGlyph.xadvance;
			} else {
				width = glyphs.width;
			}
			
			//this is identical to l.height in most cases, but we force this for consistency.
			height = Math.round(size*0.75f);
			renderedHeight = glyphs.height;
		}
	}
	/* ===== دعم العربية RTL (الإصدار الذكي) =====
	   يعكس ترتيب المقاطع في السطر، ويعكس حروف المقاطع العربية فقط،
	   ويبقي الأرقام والكلمات اللاتينية بترتيبها الطبيعي */
	private static boolean containsArabic(String s){
		if (s == null) return false;
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if ((c >= 0x0600 && c <= 0x06FF) || (c >= 0xFB50 && c <= 0xFDFF) || (c >= 0xFE70 && c <= 0xFEFF)){
				return true;
			}
		}
		return false;
	}

	private static boolean isArabicCh(char c){
		return (c >= 0x0600 && c <= 0x06FF) || (c >= 0xFB50 && c <= 0xFDFF) || (c >= 0xFE70 && c <= 0xFEFF);
	}

	private static boolean isDigit(char c){ return c >= '0' && c <= '9'; }

	private static boolean isNumPunct(char c){
		return c=='-' || c=='/' || c==':' || c=='.' || c=='+' || c=='%' || c==',';
	}

	private static String reverseLineBidi(String line){
		java.util.ArrayList<String> tokens = new java.util.ArrayList<>();
		int i = 0;
		while (i < line.length()){
			char c = line.charAt(i);
			if (isArabicCh(c)){
				int j = i;
				while (j < line.length() && isArabicCh(line.charAt(j))) j++;
				tokens.add(new StringBuilder(line.substring(i, j)).reverse().toString());
				i = j;
			} else if (isDigit(c)){
				int j = i;
				while (j < line.length() && (isDigit(line.charAt(j)) || isNumPunct(line.charAt(j)))) j++;
				while (j > i+1 && !isDigit(line.charAt(j-1))) j--;
				tokens.add(line.substring(i, j));
				i = j;
			} else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
				int j = i;
				while (j < line.length() && ((line.charAt(j) >= 'a' && line.charAt(j) <= 'z') || (line.charAt(j) >= 'A' && line.charAt(j) <= 'Z'))) j++;
				tokens.add(line.substring(i, j));
				i = j;
			} else {
				tokens.add(String.valueOf(c));
				i++;
			}
		}
		StringBuilder vis = new StringBuilder();
		for (int k = tokens.size()-1; k >= 0; k--) vis.append(tokens.get(k));
		return vis.toString();
	}

	private static String toDisplayText(String input){
		if (input == null || !containsArabic(input)) return input;
		String shaped = org.amr.arabic.ArabicUtilities.reshape(input);
		if (shaped == null) return input;
		String[] lines = shaped.split("\n", -1);
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < lines.length; i++){
			out.append(reverseLineBidi(lines[i]));
			if (i < lines.length - 1) out.append("\n");
		}
		return out.toString();
	}
	
	private float renderedHeight = 0;
	
	@Override
	protected void updateMatrix() {
		super.updateMatrix();
		//sometimes the font is rendered oddly, so we offset here to put it in the correct spot
		if (renderedHeight != height) {
			Matrix.translate(matrix, 0, Math.round(height - renderedHeight));
		}
	}
	
	private static TextRenderBatch textRenderer = new TextRenderBatch();
	
	@Override
	public synchronized void draw() {
		if (font != null) {
			updateMatrix();
			TextRenderBatch.textBeingRendered = this;
			font.draw(textRenderer, displayText, 0, 0);
		}
	}

	//implements regular PD rendering within a libGDX batch so that our rendering logic
	//can interface with the freetype font generator
	//some copypasta from BitmapText here
	private static class TextRenderBatch implements Batch {
		
		//this isn't as good as only updating once, like with BitmapText
		// but it skips almost all allocations, which is almost as good
		private static RenderedText textBeingRendered = null;
		private static float[] vertices = new float[16];
		private static HashMap<Integer, FloatBuffer> buffers = new HashMap<>();

		@Override
		public void draw(Texture texture, float[] spriteVertices, int offset, int count) {
			Visual v = textBeingRendered;
			
			FloatBuffer toOpenGL;
			if (buffers.containsKey(count/20)){
				toOpenGL = buffers.get(count/20);
				((Buffer)toOpenGL).position(0);
			} else {
				toOpenGL = Quad.createSet(count / 20);
				buffers.put(count/20, toOpenGL);
			}
			
			for (int i = 0; i < count; i += 20){
				
				vertices[0]     = spriteVertices[i+0];
				vertices[1]     = spriteVertices[i+1];
				
				vertices[2]     = spriteVertices[i+3];
				vertices[3]     = spriteVertices[i+4];
				
				vertices[4]     = spriteVertices[i+5];
				vertices[5]     = spriteVertices[i+6];
				
				vertices[6]     = spriteVertices[i+8];
				vertices[7]     = spriteVertices[i+9];
				
				vertices[8]     = spriteVertices[i+10];
				vertices[9]     = spriteVertices[i+11];
				
				vertices[10]    = spriteVertices[i+13];
				vertices[11]    = spriteVertices[i+14];
				
				vertices[12]    = spriteVertices[i+15];
				vertices[13]    = spriteVertices[i+16];
				
				vertices[14]    = spriteVertices[i+18];
				vertices[15]    = spriteVertices[i+19];
				
				toOpenGL.put(vertices);
				
			}

			((Buffer)toOpenGL).position(0);
			
			NoosaScript script = NoosaScript.get();
			
			texture.bind();
			com.watabou.glwrap.Texture.clear();
			
			script.camera( v.camera() );
			
			script.uModel.valueM4( v.matrix );
			script.lighting(
					v.rm, v.gm, v.bm, v.am,
					v.ra, v.ga, v.ba, v.aa );
			
			script.drawQuadSet( toOpenGL, count/20 );
		}
		
		//none of these functions are needed, so they are stubbed
		@Override
		public void begin() { }
		public void end() { }
		public void setColor(Color tint) { }
		public void setColor(float r, float g, float b, float a) { }
		public Color getColor() { return null; }
		public void setPackedColor(float packedColor) { }
		public float getPackedColor() { return 0; }
		public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) { }
		public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) { }
		public void draw(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) { }
		public void draw(Texture texture, float x, float y, float width, float height, float u, float v, float u2, float v2) { }
		public void draw(Texture texture, float x, float y) { }
		public void draw(Texture texture, float x, float y, float width, float height) { }
		public void draw(TextureRegion region, float x, float y) { }
		public void draw(TextureRegion region, float x, float y, float width, float height) { }
		public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) { }
		public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, boolean clockwise) { }
		public void draw(TextureRegion region, float width, float height, Affine2 transform) { }
		public void flush() { }
		public void disableBlending() { }
		public void enableBlending() { }
		public void setBlendFunction(int srcFunc, int dstFunc) { }
		public void setBlendFunctionSeparate(int srcFuncColor, int dstFuncColor, int srcFuncAlpha, int dstFuncAlpha) { }
		public int getBlendSrcFunc() { return 0; }
		public int getBlendDstFunc() { return 0; }
		public int getBlendSrcFuncAlpha() { return 0; }
		public int getBlendDstFuncAlpha() { return 0; }
		public Matrix4 getProjectionMatrix() { return null; }
		public Matrix4 getTransformMatrix() { return null; }
		public void setProjectionMatrix(Matrix4 projection) { }
		public void setTransformMatrix(Matrix4 transform) { }
		public void setShader(ShaderProgram shader) { }
		public ShaderProgram getShader() { return null; }
		public boolean isBlendingEnabled() { return false; }
		public boolean isDrawing() { return false; }
		public void dispose() { }
	}
}
