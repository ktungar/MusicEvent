package com.eventful.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import com.eventful.requestresponce.MusicEventRequestResponce;
import com.eventful.service.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Override
	public List<MusicEventRequestResponce> getEvent() {
   List<MusicEventRequestResponce> musicEventRequestResponceList=new ArrayList<MusicEventRequestResponce>();
		StringBuilder result = new StringBuilder();
		URL url;
		try {
			url = new URL(
					"http://api.eventful.com/rest/events/search?app_key=Mgxbn4GGxzVqnFjZ&keywords=music&location=London&date=Future");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			try {
				JSONObject xmlJSONObj = XML.toJSONObject(result.toString());
				String jsonPrettyPrintString = xmlJSONObj.toString(4);
				JSONArray jsonArray = xmlJSONObj.toJSONArray(new JSONArray(xmlJSONObj.getNames(xmlJSONObj)));
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					JSONObject events=jsonObject.getJSONObject("events");
					System.out.println(events);
					JSONArray eventArray=events.getJSONArray("event");
					for (int j = 0; j < eventArray.length(); j++) {
						JSONObject eventObject = eventArray.getJSONObject(j);
						MusicEventRequestResponce musicEventRequestResponce=new MusicEventRequestResponce();
						musicEventRequestResponce.setEventVenueName(eventObject.getString("venue_name"));
						musicEventRequestResponce.setEventUrl(eventObject.getString("url"));
						musicEventRequestResponce.setEventTittle(eventObject.getString("title"));
						musicEventRequestResponce.setEventStartTime(eventObject.getString("start_time"));
						musicEventRequestResponce.setEventUrl(eventObject.getString("venue_url"));
						musicEventRequestResponce.setEventVenueAddress(eventObject.getString("venue_address"));
						musicEventRequestResponce.setEventCity(eventObject.getString("city_name"));
						musicEventRequestResponce.setEventRegion(eventObject.getString("region_abbr"));
						musicEventRequestResponce.setEventCountry(eventObject.getString("country_abbr"));
						musicEventRequestResponce.setEventPostalCode(eventObject.getString("postal_code"));
						musicEventRequestResponceList.add(musicEventRequestResponce);
						System.out.println();
						System.out.println(eventObject.getString("url"));
						System.out.println(eventObject.getString("city_name"));
					}

				}

			} catch (JSONException je) {
				System.out.println(je.toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return musicEventRequestResponceList;
	}

}
