package com.loadingpanel;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.Application;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.ComponentContainer.ComponentAttachEvent;
import com.vaadin.ui.ComponentContainer.ComponentDetachEvent;
import com.vaadin.ui.Window;


public class LoadingAnimationPanel implements Component,
												RefreshListener{
	

	private static final long serialVersionUID = 4312760114461372086L;
	
	
	private VerticalLayout layout;
	
	
	private AbsoluteLayout loadingPanel;
	private Component content;
	private ProgressIndicator loadingAnimation;
	private Refresher refresher;
	private int refreshCounter = 0;
	private boolean displayContent;
	
	
	private Component parent;
	

	public LoadingAnimationPanel(){
		layout = new VerticalLayout();
		this.setSizeFull();
		layout.setParent(this);
		initLoadingPanel();
		
		refresher = new Refresher();
		refresher.addListener(this);
		setRefreshInterval(250);
		
		showLoading();
		refresh(refresher);
		layout.setImmediate(true);
		layout.addComponent(refresher);
	}
	
	private void initLoadingPanel(){
		
		loadingPanel = new AbsoluteLayout();
		loadingPanel.setSizeFull();
		
		
		loadingAnimation = new ProgressIndicator();
		loadingAnimation.setIndeterminate(true);
		loadingAnimation.setPollingInterval(500);
		loadingAnimation.setEnabled(true);
		
		// TODO how to work with external CSS files
		//loadingAnimation.setStyleName("CenteredLoadingAnimation");
		
		loadingPanel.addComponent(loadingAnimation, "top:50%; left:50%");
		
	}

	

    
    
	
	public void setNormalContent(Component component){
		this.content = component;
	}
	
	
	
	public void setRefreshInterval(int intervalInMs){
		refresher.setRefreshInterval(intervalInMs);
		loadingAnimation.setPollingInterval(intervalInMs);
	}
	
	
	public void showLoading(){
		refresher.setEnabled(true);
		displayContent = false;
		
	}
	
	public void showContent(){
		displayContent = true;
	}

	public void refresh(Refresher source) {
		
		
		if (displayContent){
			layout.removeComponent(loadingPanel);
			if (content != null) {
				layout.addComponent(content);
				layout.setExpandRatio(content, 1);
			}
			this.requestRepaint();
			refresher.setEnabled(false);
		}
		else
		{
			if (content != null) 
				layout.removeComponent(content);
			layout.addComponent(loadingPanel);
			layout.setExpandRatio(loadingPanel, 1);
			layout.requestRepaint();
		}
			
	}

	public void paint(PaintTarget target) throws PaintException {
		layout.paint(target);
	}

	public void requestRepaint() {
		layout.requestRepaint();
	}

	public void setDebugId(String id) {
		layout.setDebugId(id);
	}

	public String getDebugId() {
		return layout.getDebugId();
	}

	public void addListener(RepaintRequestListener listener) {
		layout.addListener(listener);
	}

	public void removeListener(RepaintRequestListener listener) {
		layout.removeListener(listener);
	}

	public void requestRepaintRequests() {
		layout.requestRepaintRequests();
	}

	public void changeVariables(Object source, Map<String, Object> variables) {
		layout.changeVariables(source, variables);
	}

	public boolean isImmediate() {
		return layout.isImmediate();
	}

	public float getWidth() {
		return layout.getWidth();
	}

	public void setWidth(float width) {
		layout.setWidth(width);
	}

	public float getHeight() {
		return layout.getHeight();
	}

	public void setHeight(float height) {
		layout.setHeight(height);
	}

	public int getWidthUnits() {
		return layout.getWidthUnits();
	}

	public void setWidthUnits(int units) {
		layout.setWidthUnits(units);
	}

	public int getHeightUnits() {
		return layout.getHeightUnits();
	}

	public void setHeightUnits(int units) {
		layout.setHeightUnits(units);
	}

	public void setHeight(String height) {
		layout.setHeight(height);
	}

	public void setWidth(float width, int unit) {
		layout.setWidth(width, unit);
	}

	public void setHeight(float height, int unit) {
		layout.setHeight(height, unit);
	}

	public void setWidth(String width) {
		layout.setWidth(width);
	}

	public void setSizeFull() {
		layout.setSizeFull();
	}

	public void setSizeUndefined() {
		layout.setSizeUndefined();
		
	}

	public String getStyleName() {
		return layout.getStyleName();
	}

	public void setStyleName(String style) {
		layout.setStyleName(style);
	}

	public void addStyleName(String style) {
		layout.addStyleName(style);
	}

	public void removeStyleName(String style) {
		layout.removeStyleName(style);
	}

	public boolean isEnabled() {
		return layout.isEnabled();
	}

	public void setEnabled(boolean enabled) {
		layout.setEnabled(enabled);
	}

	public boolean isVisible() {
		return layout.isVisible();
	}

	public void setVisible(boolean visible) {
		layout.setVisible(visible);
	}

	public Component getParent() {
		return parent;
	}

	public void setParent(Component parent) {
		// TODO implement
		this.parent = parent;
	}

	public boolean isReadOnly() {
		return layout.isReadOnly();
	}

	public void setReadOnly(boolean readOnly) {
		layout.setReadOnly(readOnly);
	}

	public String getCaption() {
		return layout.getCaption();
	}

	public void setCaption(String caption) {
		layout.setCaption(caption);
	}

	public Resource getIcon() {
		return layout.getIcon();
	}

	public void setIcon(Resource icon) {
		layout.setIcon(icon);
	}

	public Window getWindow() {
		return layout.getWindow();
	}

	public Application getApplication() {
		return layout.getApplication();
	}

	public void attach() {
		// TODO implement
		
	}

	public void detach() {
		// TODO implement
		
	}

	public Locale getLocale() {
		return layout.getLocale();
	}

	public void childRequestedRepaint(
			Collection<RepaintRequestListener> alreadyNotified) {
		// TODO Auto-generated method stub
		
	}

	public void addListener(Listener listener) {
		layout.addListener(listener);
	}

	public void removeListener(Listener listener) {
		layout.removeListener(listener);
	}

	
    
}
