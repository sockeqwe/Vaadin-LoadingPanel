package com.loadingpanel;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;


public class LoadingAnimationPanel extends VerticalLayout 
								implements RefreshListener{
	

	private static final long serialVersionUID = 4312760114461372086L;
	
	
	private AbsoluteLayout loadingPanel;
	private Component content;
	private ProgressIndicator loadingAnimation;
	private Refresher refresher;
	private int refreshCounter = 0;
	private boolean displayContent;

	public LoadingAnimationPanel(){
		this.setSizeFull();
		initLoadingPanel();
		
		refresher = new Refresher();
		refresher.addListener(this);
		setRefreshInterval(250);
		
		showLoading();
		refresh(refresher);
		this.setImmediate(true);
		this.addComponent(refresher);
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
			this.removeComponent(loadingPanel);
			if (content != null) {
				this.addComponent(content);
				this.setExpandRatio(content, 1);
			}
			this.requestRepaint();
			refresher.setEnabled(false);
		}
		else
		{
			if (content != null) 
				this.removeComponent(content);
			this.addComponent(loadingPanel);
			this.setExpandRatio(loadingPanel, 1);
			this.requestRepaint();
		}
			
	}
	
	 
	
}
