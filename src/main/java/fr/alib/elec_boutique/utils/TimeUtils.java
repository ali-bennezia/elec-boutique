package fr.alib.elec_boutique.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtils {

	public static Long getNowUnixEpochMilis() {
		return (long)( LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)*1000.0f );
	}
	
}
