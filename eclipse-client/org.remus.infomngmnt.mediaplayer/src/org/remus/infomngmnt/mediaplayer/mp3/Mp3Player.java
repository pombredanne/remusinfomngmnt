package org.remus.infomngmnt.mediaplayer.mp3;

import java.util.Map;

import org.eclipse.core.runtime.IPath;

import org.remus.infomngmnt.jslib.extension.CheckResourceReferenceJob;
import org.remus.infomngmnt.mediaplayer.extension.AbstractMediaPlayer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class Mp3Player extends AbstractMediaPlayer {

	public static final String REF_RES_1 = "org_remus_infomngmnt_mediaplayer_playerFlashSWF"; //$NON-NLS-1$
	public static final String REF_RES_2 = "org_remus_infomngmnt_mediaplayer_swfobjects"; //$NON-NLS-1$

	/**
	 * 
	 */
	public Mp3Player() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.mediaplayer.extension.IMediaPlayer#buildHtml(org
	 * .eclipse.core.runtime.IPath, int, int, java.util.Map)
	 */
	public String buildHtml(final IPath mediaFilePath, final int widht, final int height,
			final Map<String, String> options) {
		String htmlString = "<div id=\"mp3player\" style=\"margin-top:10px;text-align: center\"></div>\r\n"
				+ "<script type=\"text/javascript\">\r\n"
				+ "	var so = new SWFObject(\""
				+ CheckResourceReferenceJob.map.get(REF_RES_1)
				+ "\", \"swfplayer\", \""
				+ (widht == 0 ? 480 : widht)
				+ "\", \""
				+ (height == 0 ? 25 : height)
				+ "\", \"9\", \"#000000\");\r\n"
				+ "	so.addVariable(\"mp3\", \""
				+ mediaFilePath.toOSString().replaceAll("\\\\", "\\\\\\\\")
				+ "\");\r\n"
				+ "	//so.addVariable(\"autoplay\",\"true\");\r\n"
				+ "	so.addParam(\"allowFullScreen\",\"true\");\r\n"
				+ "	so.write(\"mp3player\");\r\n" + "</script>";
		return htmlString;
	}

	@Override
	public String buildHeaderScript() {
		return "<script src=\"" + CheckResourceReferenceJob.map.get(REF_RES_2)
				+ "\" type=\"text/javascript\"></script>";
	}
}
