package show;

import java.util.List;

import javax.swing.ImageIcon;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import weather.WeatherUtil;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class Weather extends ApplicationWindow {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	/**
	 * Create the application window.
	 */
	public Weather() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		Label label = new Label(container, SWT.NONE);
		label.setBounds(10, 10, 159, 17);
		label.setText("\u8BF7\u9009\u62E9\u57CE\u5E02(\u76EE\u524D\u652F\u6301\u5E7F\u4E1C\u7701)");
		
		Label l1 = new Label(container, SWT.NONE);
		l1.setBounds(10, 33, 32, 17);
		l1.setText("\u5E7F\u4E1C");
		
		final Button button = new Button(container, SWT.NONE);
		
		button.setBounds(23, 95, 80, 27);
		button.setText("\u67E5\u770B\u5929\u6C14");
		button.setVisible(false);
		
		final ComboViewer comboViewer = new ComboViewer(container, SWT.NONE);
		final Combo combo = comboViewer.getCombo();
		combo.setBounds(81, 33, 88, 25);
		
		final StyledText st = new StyledText(container, SWT.V_SCROLL|SWT.H_SCROLL);
		st.setBounds(175, 10, 249, 166);
		st.setVisible(false);
		
		final Label l_time = new Label(container, SWT.NONE);
		l_time.setBounds(33, 128, 136, 17);
		
		final Label l_wendu = new Label(container, SWT.NONE);
		l_wendu.setBounds(33, 161, 136, 17);
		
		final Label l_qk = new Label(container, SWT.NONE);
		l_qk.setBounds(33, 192, 136, 17);
		
		final Label w1 = new Label(container, SWT.NONE);
		w1.setBounds(23, 211, 32, 17);
		
		final Label w2 = new Label(container, SWT.NONE);
		w2.setBounds(71, 211, 32, 17);
		w2.setText("");
		
		

		String[] str={"韶关","江门","湛江","茂名","梅州","东莞","东源","河源","深圳","广州","惠州","汕头","珠海","佛山","汕尾"};
		 	for(String s:str)
		 	{
		 		combo.add(s);
		 	}
	        comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				
				@Override
				public void selectionChanged(SelectionChangedEvent arg0) {
					// TODO Auto-generated method stub
					button.setVisible(true);
				}
			});
	        
	        button.addSelectionListener(new SelectionAdapter() {
	        	@Override
	        	public void widgetSelected(SelectionEvent e) {
	        		System.out.println("获取当前的城市\t"+comboViewer.getCombo().getText());
	        		
	        		//获取天气预报情况
	        		//由于省份写死，是能选择广东省内城市搜索
	        		String city=comboViewer.getCombo().getText().trim();
	        		
	        		int provinceCode = WeatherUtil.getProvinceCode("广东");    //3119   
	                int cityCode = WeatherUtil.getCityCode(provinceCode, city);
	                List<String> weatherList = WeatherUtil.getWeather(cityCode); 
	                StringBuffer sb=new StringBuffer();
	                int i=0;
	                for(String weather:weatherList){  
	                	i++;
	                	if(i==3||i>7)
	                	{
	                		
	                	}else
	                	{
	                		sb.append(weather+"\n");
	                	}
	                   System.out.println(weather);  
	                   if(i==8)
	                   {
	                	   l_time.setText(weather);
	                   }
	                   if(i==9)
	                   {
	                	   l_qk.setText("气温:"+weather);
	                   }
	                   if(i==10)
	                   {
	                	   l_wendu.setText("风力:"+weather);
	                   }
	                   if(i==11)
	                   {
	                	String fileName="src/img/weather/"+weather;
	               		Image image=new Image(null, fileName);
	               		w1.setImage(image);
	                   }
	                   if(i==12)
	                   {
	                	   String fileName="src/img/weather/"+weather;
		               		Image image=new Image(null, fileName);
		               		w2.setImage(image); 
	                	   break;
	                   }
	                }  
	                st.setText(sb.toString());
	                st.setVisible(true);
	        	}
	        });
		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Weather window = new Weather();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("天气预报");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 290);
	}
}
