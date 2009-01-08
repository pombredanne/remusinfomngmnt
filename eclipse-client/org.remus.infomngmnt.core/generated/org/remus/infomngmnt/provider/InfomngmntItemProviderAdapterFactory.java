/**
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *      Tom Seidel - initial API and implementation
 * 
 *
 * $Id$
 */
package org.remus.infomngmnt.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import org.remus.infomngmnt.util.InfomngmntAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class InfomngmntItemProviderAdapterFactory extends InfomngmntAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfomngmntItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
		supportedTypes.add(IItemColorProvider.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.InformationUnit} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InformationUnitItemProvider informationUnitItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.InformationUnit}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createInformationUnitAdapter() {
		if (informationUnitItemProvider == null) {
			informationUnitItemProvider = new InformationUnitItemProvider(this);
		}

		return informationUnitItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.Usage} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UsageItemProvider usageItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.Usage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createUsageAdapter() {
		if (usageItemProvider == null) {
			usageItemProvider = new UsageItemProvider(this);
		}

		return usageItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.Category} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CategoryItemProvider categoryItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.Category}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createCategoryAdapter() {
		if (categoryItemProvider == null) {
			categoryItemProvider = new CategoryItemProvider(this);
		}

		return categoryItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.InformationUnitListItem} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InformationUnitListItemItemProvider informationUnitListItemItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.InformationUnitListItem}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createInformationUnitListItemAdapter() {
		if (informationUnitListItemItemProvider == null) {
			informationUnitListItemItemProvider = new InformationUnitListItemItemProvider(this);
		}

		return informationUnitListItemItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.ApplicationRoot} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationRootItemProvider applicationRootItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.ApplicationRoot}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createApplicationRootAdapter() {
		if (applicationRootItemProvider == null) {
			applicationRootItemProvider = new ApplicationRootItemProvider(this);
		}

		return applicationRootItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.Annotation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnotationItemProvider annotationItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.Annotation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAnnotationAdapter() {
		if (annotationItemProvider == null) {
			annotationItemProvider = new AnnotationItemProvider(this);
		}

		return annotationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.Link} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkItemProvider linkItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.Link}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createLinkAdapter() {
		if (linkItemProvider == null) {
			linkItemProvider = new LinkItemProvider(this);
		}

		return linkItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.LinkType} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkTypeItemProvider linkTypeItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.LinkType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createLinkTypeAdapter() {
		if (linkTypeItemProvider == null) {
			linkTypeItemProvider = new LinkTypeItemProvider(this);
		}

		return linkTypeItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.LinkTypeCollection} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkTypeCollectionItemProvider linkTypeCollectionItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.LinkTypeCollection}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createLinkTypeCollectionAdapter() {
		if (linkTypeCollectionItemProvider == null) {
			linkTypeCollectionItemProvider = new LinkTypeCollectionItemProvider(this);
		}

		return linkTypeCollectionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link java.util.Map.Entry} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StringToLinkTypeMapItemProvider stringToLinkTypeMapItemProvider;

	/**
	 * This creates an adapter for a {@link java.util.Map.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createStringToLinkTypeMapAdapter() {
		if (stringToLinkTypeMapItemProvider == null) {
			stringToLinkTypeMapItemProvider = new StringToLinkTypeMapItemProvider(this);
		}

		return stringToLinkTypeMapItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.RecentlyUsedKeywords} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RecentlyUsedKeywordsItemProvider recentlyUsedKeywordsItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.RecentlyUsedKeywords}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRecentlyUsedKeywordsAdapter() {
		if (recentlyUsedKeywordsItemProvider == null) {
			recentlyUsedKeywordsItemProvider = new RecentlyUsedKeywordsItemProvider(this);
		}

		return recentlyUsedKeywordsItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.NewElementRules} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NewElementRulesItemProvider newElementRulesItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.NewElementRules}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createNewElementRulesAdapter() {
		if (newElementRulesItemProvider == null) {
			newElementRulesItemProvider = new NewElementRulesItemProvider(this);
		}

		return newElementRulesItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.RuleValue} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleValueItemProvider ruleValueItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.RuleValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRuleValueAdapter() {
		if (ruleValueItemProvider == null) {
			ruleValueItemProvider = new RuleValueItemProvider(this);
		}

		return ruleValueItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.AvailableRuleDefinitions} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AvailableRuleDefinitionsItemProvider availableRuleDefinitionsItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.AvailableRuleDefinitions}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createAvailableRuleDefinitionsAdapter() {
		if (availableRuleDefinitionsItemProvider == null) {
			availableRuleDefinitionsItemProvider = new AvailableRuleDefinitionsItemProvider(this);
		}

		return availableRuleDefinitionsItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.RemusTransferType} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemusTransferTypeItemProvider remusTransferTypeItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.RemusTransferType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRemusTransferTypeAdapter() {
		if (remusTransferTypeItemProvider == null) {
			remusTransferTypeItemProvider = new RemusTransferTypeItemProvider(this);
		}

		return remusTransferTypeItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.RuleAction} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleActionItemProvider ruleActionItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.RuleAction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRuleActionAdapter() {
		if (ruleActionItemProvider == null) {
			ruleActionItemProvider = new RuleActionItemProvider(this);
		}

		return ruleActionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.RuleResult} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleResultItemProvider ruleResultItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.RuleResult}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRuleResultAdapter() {
		if (ruleResultItemProvider == null) {
			ruleResultItemProvider = new RuleResultItemProvider(this);
		}

		return ruleResultItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.RemoteRepository} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoteRepositoryItemProvider remoteRepositoryItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.RemoteRepository}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRemoteRepositoryAdapter() {
		if (remoteRepositoryItemProvider == null) {
			remoteRepositoryItemProvider = new RemoteRepositoryItemProvider(this);
		}

		return remoteRepositoryItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.RemoteObject} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoteObjectItemProvider remoteObjectItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.RemoteObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRemoteObjectAdapter() {
		if (remoteObjectItemProvider == null) {
			remoteObjectItemProvider = new RemoteObjectItemProvider(this);
		}

		return remoteObjectItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.RemoteContainer} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoteContainerItemProvider remoteContainerItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.RemoteContainer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRemoteContainerAdapter() {
		if (remoteContainerItemProvider == null) {
			remoteContainerItemProvider = new RemoteContainerItemProvider(this);
		}

		return remoteContainerItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.RepositoryCollection} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepositoryCollectionItemProvider repositoryCollectionItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.RepositoryCollection}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createRepositoryCollectionAdapter() {
		if (repositoryCollectionItemProvider == null) {
			repositoryCollectionItemProvider = new RepositoryCollectionItemProvider(this);
		}

		return repositoryCollectionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.SynchronizationMetadata} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SynchronizationMetadataItemProvider synchronizationMetadataItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.SynchronizationMetadata}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSynchronizationMetadataAdapter() {
		if (synchronizationMetadataItemProvider == null) {
			synchronizationMetadataItemProvider = new SynchronizationMetadataItemProvider(this);
		}

		return synchronizationMetadataItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.ChangeSet} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChangeSetItemProvider changeSetItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.ChangeSet}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createChangeSetAdapter() {
		if (changeSetItemProvider == null) {
			changeSetItemProvider = new ChangeSetItemProvider(this);
		}

		return changeSetItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.remus.infomngmnt.ChangeSetItem} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChangeSetItemItemProvider changeSetItemItemProvider;

	/**
	 * This creates an adapter for a {@link org.remus.infomngmnt.ChangeSetItem}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createChangeSetItemAdapter() {
		if (changeSetItemItemProvider == null) {
			changeSetItemItemProvider = new ChangeSetItemItemProvider(this);
		}

		return changeSetItemItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link java.util.Map.Entry} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CategoryToSynchronizationActionMapItemProvider categoryToSynchronizationActionMapItemProvider;

	/**
	 * This creates an adapter for a {@link java.util.Map.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createCategoryToSynchronizationActionMapAdapter() {
		if (categoryToSynchronizationActionMapItemProvider == null) {
			categoryToSynchronizationActionMapItemProvider = new CategoryToSynchronizationActionMapItemProvider(this);
		}

		return categoryToSynchronizationActionMapItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link java.util.Map.Entry} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InformationUnitListItemToInformationUnitMapItemProvider informationUnitListItemToInformationUnitMapItemProvider;

	/**
	 * This creates an adapter for a {@link java.util.Map.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createInformationUnitListItemToInformationUnitMapAdapter() {
		if (informationUnitListItemToInformationUnitMapItemProvider == null) {
			informationUnitListItemToInformationUnitMapItemProvider = new InformationUnitListItemToInformationUnitMapItemProvider(this);
		}

		return informationUnitListItemToInformationUnitMapItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (informationUnitItemProvider != null) informationUnitItemProvider.dispose();
		if (usageItemProvider != null) usageItemProvider.dispose();
		if (categoryItemProvider != null) categoryItemProvider.dispose();
		if (informationUnitListItemItemProvider != null) informationUnitListItemItemProvider.dispose();
		if (applicationRootItemProvider != null) applicationRootItemProvider.dispose();
		if (annotationItemProvider != null) annotationItemProvider.dispose();
		if (linkItemProvider != null) linkItemProvider.dispose();
		if (linkTypeItemProvider != null) linkTypeItemProvider.dispose();
		if (linkTypeCollectionItemProvider != null) linkTypeCollectionItemProvider.dispose();
		if (stringToLinkTypeMapItemProvider != null) stringToLinkTypeMapItemProvider.dispose();
		if (recentlyUsedKeywordsItemProvider != null) recentlyUsedKeywordsItemProvider.dispose();
		if (newElementRulesItemProvider != null) newElementRulesItemProvider.dispose();
		if (ruleValueItemProvider != null) ruleValueItemProvider.dispose();
		if (availableRuleDefinitionsItemProvider != null) availableRuleDefinitionsItemProvider.dispose();
		if (remusTransferTypeItemProvider != null) remusTransferTypeItemProvider.dispose();
		if (ruleActionItemProvider != null) ruleActionItemProvider.dispose();
		if (ruleResultItemProvider != null) ruleResultItemProvider.dispose();
		if (remoteRepositoryItemProvider != null) remoteRepositoryItemProvider.dispose();
		if (remoteObjectItemProvider != null) remoteObjectItemProvider.dispose();
		if (remoteContainerItemProvider != null) remoteContainerItemProvider.dispose();
		if (repositoryCollectionItemProvider != null) repositoryCollectionItemProvider.dispose();
		if (synchronizationMetadataItemProvider != null) synchronizationMetadataItemProvider.dispose();
		if (changeSetItemProvider != null) changeSetItemProvider.dispose();
		if (changeSetItemItemProvider != null) changeSetItemItemProvider.dispose();
		if (categoryToSynchronizationActionMapItemProvider != null) categoryToSynchronizationActionMapItemProvider.dispose();
		if (informationUnitListItemToInformationUnitMapItemProvider != null) informationUnitListItemToInformationUnitMapItemProvider.dispose();
	}

}
