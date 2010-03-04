/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.rules.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.services.IInformationTypeHandler;
import org.remus.infomngmnt.services.RemusServiceTracker;
import org.remus.infomngmnt.ui.infotypes.service.IInformationTypeImage;
import org.remus.rules.RuleAction;
import org.remus.rules.RulesFactory;
import org.remus.rules.RulesPackage;

/**
 * This is the item provider adapter for a {@link org.remus.rules.RuleAction}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class RuleActionItemProvider extends ItemProviderAdapter implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource {
	/**
	 * @generated not
	 */
	private final RemusServiceTracker serviceTracker;
	private final IInformationTypeHandler infoTypeService;
	private final IInformationTypeImage infoTypeImageService;

	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated not
	 */
	public RuleActionItemProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
		this.serviceTracker = RuleEditPlugin.getPlugin().getServiceTracker();
		this.infoTypeService = this.serviceTracker.getService(IInformationTypeHandler.class);
		this.infoTypeImageService = this.serviceTracker.getService(IInformationTypeImage.class);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(final Object object) {
		if (this.itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addInfoTypeIdPropertyDescriptor(object);
			addGroovyMatcherPropertyDescriptor(object);
			addPostProcessingInstructionsPropertyDescriptor(object);
		}
		return this.itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addNamePropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_RuleAction_name_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_RuleAction_name_feature",
						"_UI_RuleAction_type"), RulesPackage.Literals.RULE_ACTION__NAME, true,
				false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Info Type Id feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addInfoTypeIdPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_RuleAction_infoTypeId_feature"), getString(
						"_UI_PropertyDescriptor_description", "_UI_RuleAction_infoTypeId_feature",
						"_UI_RuleAction_type"), RulesPackage.Literals.RULE_ACTION__INFO_TYPE_ID,
				true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Groovy Matcher feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addGroovyMatcherPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
				getResourceLocator(), getString("_UI_RuleAction_groovyMatcher_feature"), getString(
						"_UI_PropertyDescriptor_description",
						"_UI_RuleAction_groovyMatcher_feature", "_UI_RuleAction_type"),
				RulesPackage.Literals.RULE_ACTION__GROOVY_MATCHER, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Post Processing Instructions
	 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addPostProcessingInstructionsPropertyDescriptor(final Object object) {
		this.itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory)
						.getRootAdapterFactory(), getResourceLocator(),
						getString("_UI_RuleAction_postProcessingInstructions_feature"), getString(
								"_UI_PropertyDescriptor_description",
								"_UI_RuleAction_postProcessingInstructions_feature",
								"_UI_RuleAction_type"),
						RulesPackage.Literals.RULE_ACTION__POST_PROCESSING_INSTRUCTIONS, true,
						false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to
	 * deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in
	 * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(final Object object) {
		if (this.childrenFeatures == null) {
			super.getChildrenFeatures(object);
			this.childrenFeatures.add(RulesPackage.Literals.RULE_ACTION__RULE_VALUE);
		}
		return this.childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(final Object object, final Object child) {
		// Check the type of the specified child object and return the proper
		// feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns RuleAction.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated not
	 */
	@Override
	public Object getImage(final Object object) {
		return this.infoTypeImageService.getImageByInfoType(((RuleAction) object).getInfoTypeId());

	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated not
	 */
	@Override
	public String getText(final Object object) {
		String infoTypeId = ((RuleAction) object).getInfoTypeId();
		IInfoType infoTypeByType = this.infoTypeService.getInfoTypeByType(infoTypeId);
		String returnValue = ((RuleAction) object).getName();
		if (returnValue == null || returnValue.length() == 0) {
			if (infoTypeByType == null) {
				returnValue = infoTypeId;
			} else {
				returnValue = infoTypeByType.getName();
			}
		}
		return returnValue;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(final Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(RuleAction.class)) {
		case RulesPackage.RULE_ACTION__NAME:
		case RulesPackage.RULE_ACTION__INFO_TYPE_ID:
		case RulesPackage.RULE_ACTION__GROOVY_MATCHER:
		case RulesPackage.RULE_ACTION__POST_PROCESSING_INSTRUCTIONS:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(),
					false, true));
			return;
		case RulesPackage.RULE_ACTION__RULE_VALUE:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(),
					true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(final Collection<Object> newChildDescriptors,
			final Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(RulesPackage.Literals.RULE_ACTION__RULE_VALUE,
				RulesFactory.eINSTANCE.createRuleValue()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return RuleEditPlugin.INSTANCE;
	}

}
