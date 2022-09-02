ALTER table videosource convert to charset utf8;
DELETE from videosource;
INSERT INTO videosource(channel_title, channel_Id, source_type) VALUES ('lck','UCw1DsweY9b2AKGjV4kGJP1A','youtube');
INSERT INTO videosource(channel_title, channel_Id, source_type) VALUES ('T1','UCJprx3bX49vNl6Bcw01Cwfg','youtube');
INSERT INTO videosource(channel_title, channel_Id, source_type) VALUES ('리브 샌드박스 Liiv SANDBOX','UCxedTJNaGRHiq6YfNtQVCNA','youtube');
INSERT INTO videosource(channel_title, channel_Id, source_type) VALUES ('Gen.G esports','UCDmmbxGg8g-EBkC_ku6vybg','youtube');
INSERT INTO videosource(channel_title, channel_Id, source_type) VALUES ('한화생명e스포츠','UCrfB1-zWijAYkgfZW7Ehc8Q','youtube');
INSERT INTO videosource(channel_title, channel_Id, source_type) VALUES ('DWGKIA','UCepHesz_5Lwr7qRaqjB-p1A','youtube');

ALTER table video convert to charset utf8;