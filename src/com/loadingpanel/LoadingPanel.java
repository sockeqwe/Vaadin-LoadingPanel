package com.loadingpanel;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;

/**
 * This is a GUI component, that gives you the possibility to switch simply between displaying some content
 * and a loading animantion. In combination with the Refresher add-on and {@link Thread}s you can implement 
 * async ajax requests.
 * Use {@link #setNormalContent(Component)} to set the normal content, that is displayed when no loading animation
 * is on the screen. Use {@link #showContent()} and {@link #showLoading()} to switch between displaying the content and
 * the loading animation.
 * 
 * <b> Note: </b> The {@link LoadingPanel} is implemented as a {@link VerticalLayout} and therefore methods like {@link #addComponent(Component)}
 * are available. To guarantee a correct behavior you are not allowed to use any of the {@link VerticalLayout}s methods to add Components.
 * <b>Use {@link #setNormalContent(Component)} only!</b>
 * @author Hannes Dorfmann
 *
 */
public class LoadingPanel extends VerticalLayout 
								implements RefreshListener{
	

	private static final long serialVersionUID = 4312760114461372086L;
	
	
	private AbsoluteLayout loadingPanel;
	private Component content;
	private ProgressIndicator loadingAnimation;
	private Refresher refresher;
	private int refreshCounter = 0;
	private boolean displayContent;

	public LoadingPanel(){
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


	/**
	 * Use this method, to specify, which content should be displayed on screen by calling
	 * {@link #showContent()}
	 * @param component
	 */
	public void setNormalContent(Component component){
		this.content = component;
	}
	
	
	/**
	 * Set the interval time for polling updates from server.
	 * @param intervalInMs
	 */
	public void setRefreshInterval(int intervalInMs){
		refresher.setRefreshInterval(intervalInMs);
		loadingAnimation.setPollingInterval(intervalInMs);
	}
	
	/**
	 * Shows a loading animation ({@link ProgressIndicator}) on screen.
	 * That means, that the normal content is removed from screen and replaced by the loading animation.
	 */
	public void showLoading(){
		refresher.setEnabled(true);
		displayContent = false;
		
	}
	
	/**
	 * Shows the {@link Component} that is specified with {@link #setNormalContent(Component)}.
	 * That means, that the loading animation is removed from screen and replaced by the normal content.
	 */
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
