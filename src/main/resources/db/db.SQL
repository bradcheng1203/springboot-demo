
DROP TABLE IF EXISTS `CurrencyInfo`;

CREATE TABLE `CurrencyInfo` (
 `ID` int(11) NOT NULL AUTO_INCREMENT,  
 `Date` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
 `USD_NTD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
 `RMB_NTD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
 `EUR_USD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
 `USD_JPY` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
 `GBP_USD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
 `AUD_USD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
 `USD_HKD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
 `USD_RMB` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
 `USD_ZAR` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
 `NZD_USD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
 `CREATE_DATE` datetime DEFAULT NULL,
 PRIMARY KEY (`ID`)
)
