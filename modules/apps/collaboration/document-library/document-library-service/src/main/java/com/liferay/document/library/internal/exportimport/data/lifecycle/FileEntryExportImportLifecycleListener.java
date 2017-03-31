/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.internal.exportimport.data.lifecycle;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lifecycle.EventAwareExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Shay
 */
@Component(service = ExportImportLifecycleListener.class)
public class FileEntryExportImportLifecycleListener
	implements EventAwareExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	public void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {
	}

	@Override
	public void onLayoutExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {
	}

	@Override
	public void onLayoutExportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onLayoutExportSucceeded(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onLayoutImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_cleanUpFileEntries();
	}

	@Override
	public void onLayoutImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onLayoutImportStarted(PortletDataContext portletDataContext)
		throws Exception {

		_resetFileEntries();
	}

	@Override
	public void onLayoutImportSucceeded(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onLayoutLocalPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {

		_cleanUpFileEntries();
	}

	@Override
	public void onLayoutLocalPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_resetFileEntries();
	}

	@Override
	public void onLayoutLocalPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	@Override
	public void onLayoutRemotePublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {

		_cleanUpFileEntries();
	}

	@Override
	public void onLayoutRemotePublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	@Override
	public void onLayoutRemotePublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_resetFileEntries();
	}

	@Override
	public void onPortletExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {
	}

	@Override
	public void onPortletExportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onPortletExportSucceeded(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onPortletImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		String rootPortletId = portletDataContext.getRootPortletId();

		if (!rootPortletId.equals(DLPortletKeys.DOCUMENT_LIBRARY) &&
			!rootPortletId.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return;
		}

		_cleanUpFileEntries();
	}

	@Override
	public void onPortletImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception {

		_resetFileEntries();
	}

	@Override
	public void onPortletImportStarted(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onPortletImportSucceeded(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onPortletPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {
	}

	@Override
	public void onPortletPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	@Override
	public void onPortletPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {
	}

	@Override
	public void onStagedModelExportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {
	}

	@Override
	public void onStagedModelExportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

	@Override
	public void onStagedModelExportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

	@Override
	public void onStagedModelImportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {
	}

	@Override
	public void onStagedModelImportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {
	}

	@Override
	public void onStagedModelImportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		String rootPortletId = portletDataContext.getRootPortletId();

		if (!rootPortletId.equals(DLPortletKeys.DOCUMENT_LIBRARY) &&
			!rootPortletId.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return;
		}

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		String mapName = PortletDataHandlerControl.getNamespacedControlName(
			rootPortletId, "new-model");

		String[] newModelParameters = parameterMap.get(mapName);

		if (portletDataContext.getBooleanParameter(
				rootPortletId, "new-model", false)) {

			long fileEntryId = ((FileEntry)stagedModel).getFileEntryId();

			_fileEntryIds.add(fileEntryId);

			_fileEntryCompanyIds.put(
				fileEntryId, GetterUtil.getLong(newModelParameters[1]));
			_fileEntryRepositoryIds.put(
				fileEntryId, GetterUtil.getLong(newModelParameters[2]));
			_fileEntryNames.put(fileEntryId, newModelParameters[3]);

			//_fileEntries.add((FileEntry)stagedModel);
		}
		else {
			_fileEntryIds.add(GetterUtil.getLong(newModelParameters[1]));

			// TODO: add the fileEntry to restore . . .

		}
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setDlFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	private void _cleanUpFileEntries() {

		// TODO: restore fileEntries that were moved/deleted/updated . . .

		for (Long fileEntryId : _fileEntryIds) {
			long companyId = _fileEntryCompanyIds.get(fileEntryId);
			long repositoryId = _fileEntryRepositoryIds.get(fileEntryId);
			String fileName = _fileEntryNames.get(fileEntryId);

			try {
				DLStoreUtil.deleteFile(companyId, repositoryId, fileName);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Failed to clean up file entry: " + fileEntryId);
				}
			}
		}

		_resetFileEntries();
	}

	private void _resetFileEntries() {
		_fileEntries = new HashMap<>();
		_fileEntryCompanyIds = new HashMap<>();
		_fileEntryIds = new LinkedList<>();
		_fileEntryNames = new HashMap<>();
		_fileEntryRepositoryIds = new HashMap<>();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryExportImportLifecycleListener.class);

	private DLAppLocalService _dlAppLocalService;
	private DLFileEntryLocalService _dlFileEntryLocalService;
	private Map<Long, FileEntry> _fileEntries = new HashMap<>();
	private Map<Long, Long> _fileEntryCompanyIds = new HashMap<>();
	private List<Long> _fileEntryIds = new LinkedList<>();
	private Map<Long, String> _fileEntryNames = new HashMap<>();
	private Map<Long, Long> _fileEntryRepositoryIds = new HashMap<>();

}