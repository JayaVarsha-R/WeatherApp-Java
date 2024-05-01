import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

public class WeatherAppGui extends JFrame{
  public JSONObject weatherData;



  public WeatherAppGui()
  {
    //set up our gui and add a title
    super("Weather App");

    //configure gui to end the program's process once it has been closed
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    //set the size of our gui (in pixels)
     setSize(450,650);
     
     //load our gui at the center of the screen
     setLocationRelativeTo(null);
     
     //make our layout manager null to maually position our components with  the gui
     setLayout(null);

     //prevent any resize of our gui
     setResizable(false);

     addGuiComponents();
  }

  private void addGuiComponents(){
    //search field
    JTextField searchTextField = new JTextField();

    //set the location and size of our component
    searchTextField.setBounds(15,15,351,45);

    //change the font style and size
    searchTextField.setFont(new Font("Poppins",Font.PLAIN,24));

    add(searchTextField);

    //wetaher image
    JLabel weatherConditionImage = new JLabel(loadImage("D:\\Users\\Public\\WeatherAppGUI\\src\\assets\\cloudy.png"));
    weatherConditionImage.setBounds(0,125,450,217);
    add(weatherConditionImage);

    //temperature text
    JLabel temperatureText =new JLabel("10 C");
    temperatureText.setBounds(0,350,450,54);
    temperatureText.setFont(new Font("Poppins",Font.BOLD,48));

    //center the text
    temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
    add(temperatureText);

    //weather condition description
    JLabel weatherConditionDesc = new JLabel("Cloudy");
    weatherConditionDesc.setBounds(0,405,450,36);
    weatherConditionDesc.setFont(new Font("Poppins",Font.PLAIN,32));
    weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
    add(weatherConditionDesc);

    //humidity image
    JLabel humidityImage = new JLabel(loadImage("D:\\Users\\Public\\WeatherAppGUI\\src\\assets\\humidity.png"));
    humidityImage.setBounds(15,500,74,66);
    add(humidityImage);

    //humidity text
    JLabel humidityText = new JLabel("<html><b>Humidity</b> 100% </html>");
    humidityText.setBounds(90,500,85,55);
    humidityText.setFont(new Font("Poppins",Font.PLAIN,16));
    add(humidityText);

    //windspeed image
    JLabel windspeedImage = new JLabel(loadImage("D:\\Users\\Public\\WeatherAppGUI\\src\\assets\\windspeed.png"));
    windspeedImage.setBounds(220,500,74,66);
    add(windspeedImage);

    //windspeed text
    JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15kmph </html>");
    windspeedText.setBounds(310,500,85,55);
    windspeedText.setFont(new Font("Poppins",Font.PLAIN,16));
    add(windspeedText);

    //search button
    JButton searchButton = new JButton(loadImage("D:\\Users\\Public\\WeatherAppGUI\\src\\assets\\search.png"));
    
    //change the cursor to a hand cursor when hovering the button
    searchButton.setCursor(Cursor.getPredefinedCursor( Cursor.HAND_CURSOR));
    searchButton.setBounds(375,13,47,45);
    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e){
        //get location from user
        String userInput = searchTextField.getText();

        //validate input - remove whitespace to ensure non-empty text
        if(userInput.replaceAll("\\s","").length() <=0){
          return;
        }

        //retrieve weather data
        weatherData = WeatherApp.getWeatherData(userInput);

        //update gui
         
        //update weather image
        String weatherCondition = (String) weatherData.get("weather_condition");
 
        //depending on the condition, we will update the weather image that corresponds with the condition
        switch(weatherCondition){
          case "Clear":
          weatherConditionImage.setIcon(loadImage("D:\\Users\\Public\\WeatherAppGUI\\src\\assets\\clear.png"));
          break;
          case "Cloudy":
          weatherConditionImage.setIcon(loadImage("D:\\Users\\Public\\WeatherAppGUI\\src\\assets\\cloudy.png"));
          break;
          case "Rain":
          weatherConditionImage.setIcon(loadImage("D:\\Users\\Public\\WeatherAppGUI\\src\\assets\\rain.png"));
          break;
          case "Snow":
          weatherConditionImage.setIcon(loadImage("D:\\Users\\Public\\WeatherAppGUI\\src\\assets\\snow.png"));
          break;

        }

        //update temperature text
        double temperature = (double) weatherData.get("temperature");
        temperatureText.setText(temperature + "C");

        //update weather condition text
        weatherConditionDesc.setText(weatherCondition);

        //update humidity text
        long humidity = (long) weatherData.get("humidity");
        humidityText.setText("<html><b>Humidity</b>"+ humidity +"%</html>");

        //update windspeed text
        double windspeed = (double) weatherData.get("windspeed");
        windspeedText.setText("<html><b>Windspeed</b>"+ windspeed +"km/h</html>");
      }
    });
    add(searchButton);
    }
  // read the image file from the path given
  private ImageIcon loadImage(String resourcePath){
    try{
       BufferedImage image = ImageIO.read(new File(resourcePath));

       //return on image so that our component can render it 
       return new ImageIcon(image);
    }catch(IOException e){
      e.printStackTrace();
    }

    System.out.println("Could not find resource");
    return null;
  }
}
