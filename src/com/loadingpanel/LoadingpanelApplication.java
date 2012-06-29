package com.loadingpanel;

import java.util.Random;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LoadingpanelApplication extends Application {

	private static final long serialVersionUID = -2253329315596237729L;

	private Button startButton;
	private Table resultTable;
	private LoadingAnimationPanel loadingPanel;
	
	private static final String startButtonCaption =
			"Click here to start a query, that needs 5 sconds to complete";
	
	private static final String startButtonLoadingCaption =
			"Quering, please wait";
	
	@Override
	public void init() {
		
		VerticalLayout layout = new VerticalLayout();
		startButton = new Button(startButtonCaption);
		startButton.addListener(new ClickListener() {
			
			private static final long serialVersionUID = 8655364715774510966L;

			public void buttonClick(ClickEvent event) {
				doDatabeseQuery();
			}
		});
		
		
		resultTable = new Table();
		resultTable.setSizeFull();
		resultTable.addContainerProperty("Column1", String.class, null);
		resultTable.addContainerProperty("Column2", String.class, null);
		resultTable.addContainerProperty("Column3", String.class, null);
		
		
		loadingPanel = new LoadingAnimationPanel();
		loadingPanel.setSizeFull();
		loadingPanel.setNormalContent(resultTable);
		doDatabeseQuery();
		
		layout.addComponent(startButton);
		layout.addComponent(loadingPanel);
		layout.setExpandRatio(loadingPanel, 1);
		layout.setSizeFull();
		
		Window mainWindow = new Window("LoadingPanel Demo", layout);
		setMainWindow(mainWindow);
		mainWindow.setSizeFull();
	}

	
	/**
	 * Simulates a asynchronous executed database query using a new Thread
	 */
	private void doDatabeseQuery(){
		
		startButton.setEnabled(false);
		startButton.setCaption(startButtonLoadingCaption);
		
		// The query starts, so the loading animation should be displayed
		loadingPanel.showLoading();
		
		
		Thread queryThread = new Thread(){
			public void run(){
				
				// Simulate that the query needs 5 seconds to complete
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				resultTable.removeAllItems();
				generateRandomData();
				
				// after the data has been loaded completely,
				// we switch the LoadingAnimationPanel to display the content
				// instead of the loading animation
				loadingPanel.showContent();
				
				startButton.setCaption(startButtonCaption);
				startButton.setEnabled(true);
			}
		};
		
		
		queryThread.start();
		
	}
	
	
	
	/**
	 * Fulfills the table with random data
	 */
	private void generateRandomData(){
		int rows = 200;
		
		for (int i = 0 ; i < rows; i++){
			String [] cells = new String[3];
			cells[0] = randomString(10);
			cells[1] = randomString(10);
			cells[2] = randomString(10);
			
			resultTable.addItem(cells, i);
		}
		
	}

	
	
	
	// Ugly but simple string randomizer
	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random rnd = new Random();

	String randomString( int len ) 
	{
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}

}
