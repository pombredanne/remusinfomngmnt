package org.remus.infomngmnt.connector.flickr.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.connector.flickr.messages.messages"; //$NON-NLS-1$
	public static String CopyImageUrlToClipboradHandler_ContactFlickr;
	public static String CopyImageUrlToClipboradHandler_Error;
	public static String CopyImageUrlToClipboradHandler_ErrorContactFlickr;
	public static String CopyImageUrlToClipboradHandler_ErrorCopyingUrl;
	public static String FlickConnectionWizardPage_FollowInstructions;
	public static String FlickConnectionWizardPage_GrantAccess;
	public static String FlickrConnector_AddingPhotosToFavorites;
	public static String FlickrConnector_CommittingPhotosInFavsNotAllowed;
	public static String FlickrConnector_DeletingFavFolderNotAllowed;
	public static String FlickrConnector_ErrorCollectingData;
	public static String FlickrConnector_ErrorComittingPhoto;
	public static String FlickrConnector_ErrorCreatingPhotoSet;
	public static String FlickrConnector_ErrorDeletingPhotoFavs;
	public static String FlickrConnector_ErrorDeletingPhotoset;
	public static String FlickrConnector_ErrorDownloading;
	public static String FlickrConnector_ErrorInitECF;
	public static String FlickrConnector_ErrorLoadingPhoto;
	public static String FlickrConnector_ErrorRemovingPhoto;
	public static String FlickrConnector_ErrorResolving;
	public static String FlickrConnector_ErrorResolvingItem;
	public static String FlickrConnector_Favorites;
	public static String FlickrConnector_MySets;
	public static String NewFlickrRepositoryWizard_CheckCredentials;
	public static String NewFlickrRepositoryWizard_Error;
	public static String NewFlickrRepositoryWizard_ErrorFinish;
	public static String NewFlickrRepositoryWizard_ErrorInit;
	public static String NewFlickrRepositoryWizard_ErrorValidatingCredentials;
	public static String NewFlickrRepositoryWizard_FlickRepository;
	public static String OpenElementOnFlickrHandler_ContactingFlickr;
	public static String OpenElementOnFlickrHandler_Error;
	public static String OpenElementOnFlickrHandler_ErrorContactingFlickr;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
