<?xml version="1.0" encoding="ISO-8859-1"?>
<helpset>
  <title>Helium Hilfe</title>
  <maps>
    <homeID>roadmap_htm</homeID>
    <mapref location="map.jhm"/>
    <mapref location="map3.jhm"/>
  </maps>
  <view>
    <name>Inhaltsverzeichnis</name>
    <label>Inhaltsverzeichnis</label>
    <type>javax.help.TOCView</type>
    <data>toc.xml</data>
  </view>
  <view>
    <name>Suche</name>
    <label>Suche</label>
    <type>javax.help.SearchView</type>
    <data engine="com.sun.java.help.search.DefaultSearchEngine">searchDB</data>
  </view>
  <view>
    <name>Index</name>
    <label>Index</label>
    <type>javax.help.IndexView</type>
    <data>index.xml</data>
  </view>
  <presentation default="true" displayviewimages="true">
    <name>Helium Hilfe Hauptfenster</name>
    <size height="600" width="800"/>
    <location x="200" y="200"/>
    <title>HELIUM V 5.10. Online Help.</title>
    <image>toplevelfolder</image>
    <toolbar>
      <helpaction image="HeliumV_Logo_jpg">
	    javax.help.HomeAction
	</helpaction>
      <helpaction>javax.help.BackAction</helpaction>
      <helpaction>javax.help.ForwardAction</helpaction>
      <helpaction>javax.help.SeparatorAction</helpaction>
      <helpaction>javax.help.ReloadAction</helpaction>
      <helpaction>javax.help.SeparatorAction</helpaction>
      <helpaction>javax.help.PrintAction</helpaction>
      <helpaction>javax.help.PrintSetupAction</helpaction>
    </toolbar>
  </presentation>
</helpset>
