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

package com.liferay.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.io.InputStream;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * Display renderer used to describe and render models of a given type. If an
 * exception occurs during rendering, the default renderer is used instead.
 *
 * @author Samuel Trong Tran
 * @see    DisplayContext
 */
public interface CTDisplayRenderer<T> {

	/**
	 * Returns the html content for the model. The default implementation throws
	 * an <code>UnsupportedOperationException</code>.
	 *
	 * @param  liferayPortletRequest the portlet request
	 * @param  liferayPortletResponse the portlet response
	 * @param  model the model for this display renderer
	 * @return the html content
	 * @throws Exception if an exception occurred
	 */
	public default String getContent(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, T model)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the input stream for the model and key from when the URL was
	 * generated during rendering.
	 *
	 * @param  model the model for the download
	 * @param  key the key used when creating the download URL
	 * @return the input stream
	 * @see    DisplayContext#getDownloadURL(String, long, String)
	 */
	public default InputStream getDownloadInputStream(T model, String key)
		throws PortalException {

		return null;
	}

	/**
	 * Returns the edit URL for the model (optionally <code>null</code>).
	 *
	 * @param  httpServletRequest the request
	 * @param  model the model to be edited
	 * @return the URL to use for editing the model
	 * @throws Exception if an exception occurred
	 */
	public String getEditURL(HttpServletRequest httpServletRequest, T model)
		throws Exception;

	/**
	 * Returns the model class for this display renderer.
	 *
	 * @return the model class for this display renderer
	 */
	public Class<T> getModelClass();

	/**
	 * Returns the html content for the previous versioned model of two
	 * versioned models. Both the previous and the current versioned models are
	 * provided to allow correctly determining the locale. The default
	 * implementation throws an <code>UnsupportedOperationException</code>.
	 *
	 * @param  liferayPortletRequest the portlet request
	 * @param  liferayPortletResponse the portlet response
	 * @param  currentModel the current versioned model
	 * @param  previousModel the the previous versioned model
	 * @return the html content of the previous versioned model
	 * @throws Exception if an exception occurred
	 */
	public default String getPreviousContent(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, T currentModel,
			T previousModel)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the previous version of the provided versioned model. The default
	 * implementation throws an <code>UnsupportedOperationException</code>.
	 *
	 * @param  model the versioned model
	 * @return the previous versioned model
	 * @throws PortalException if an exception occurred
	 */
	public default T getPreviousVersionedModel(T model) throws PortalException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the title for the model.
	 *
	 * @param  locale to use for translation
	 * @param  model the model for this display renderer
	 * @return the title for the model
	 * @throws PortalException if a portal exception occurred
	 */
	public String getTitle(Locale locale, T model) throws PortalException;

	/**
	 * Returns the translated type name for the model type.
	 *
	 * @param  locale to use for translation
	 * @return the type name for the model type
	 */
	public String getTypeName(Locale locale);

	/**
	 * Returns the version name for the model. The default implementation throws
	 * an <code>UnsupportedOperationException</code>.
	 *
	 * @param  model the model for this display renderer
	 * @return the version name for the model
	 */
	public default String getVersionName(T model) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns whether the model has an html content. The default implementation
	 * returns <code>false</code>.
	 *
	 * @return whether the model has an html content
	 */
	public default boolean hasContent() {
		return false;
	}

	/**
	 * Returns whether the model may be hidden by default. Hidden models may be
	 * filtered out in some views.
	 *
	 * @param  model the model to be shown or hidden by default
	 * @return whether the model may be hidden by default
	 */
	public default boolean isHideable(T model) {
		return false;
	}

	/**
	 * Returns whether the model is versioned. The default implementation
	 * returns <code>false</code>.
	 *
	 * @return whether the model is versioned
	 */
	public default boolean isVersioned() {
		return false;
	}

	/**
	 * Renders the model with the display context.
	 *
	 * @param  displayContext the context for rendering the model
	 * @throws Exception if an exception occurred
	 */
	public void render(DisplayContext<T> displayContext) throws Exception;

}