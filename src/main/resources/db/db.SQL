
DROP TABLE IF EXISTS `CurrencyInfo`;

CREATE TABLE `CurrencyInfo` (
  `Date` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `USDNTD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `RMBNTD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `EURUSD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `USDJPY` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `GBPUSD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AUDUSD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `USDHKD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `USDRMB` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `USDZAR` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `NZDUSD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `CREATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`Date`)
)
