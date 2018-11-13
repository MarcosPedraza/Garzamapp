package com.uaeh.garza.garzamapp;


import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.uaeh.garza.garzamapp.Receivers.SmsReceiver;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private static  final String TAG = "MapsActivity";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";


    private static final int ICON_PARADA = R.mipmap.ic_marker_par;
    private static final int ICON_ESCUELA = R.drawable.icon_par;

    private static final int SMS_PERMISSION_CODE = 12;


    //Escuchador de mensaje
    SmsReceiver receiver = new SmsReceiver();

    //widgets
    private MapView mMapView;
    private Toolbar toolbar;
    private ImageView img_info_m;
    private TextView tv_info_marker;

    Marker bus_pos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        toolbar = findViewById(R.id.toolbar_map);
        img_info_m = findViewById(R.id.info_icon_marker);
        tv_info_marker = findViewById(R.id.tv_title_marker);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);


        if(!isSmsPermissionGranted())
        {
            requestReadAndSendSmsPermission();
        }


    }

    //comportamiento de la flecha atras
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }



    //este metodo se ejecuta cuando el mapa esta lista
    @Override
    public void onMapReady(final GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));


        loadPolylines(map);

        setMarkers(map);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.09309534136533, -98.71148228645326), 12));
        map.animateCamera(CameraUpdateFactory.zoomTo(12));
        map.setOnMarkerClickListener(this);

        receiver.setListener(new SmsReceiver.Listener() {
            @Override
            public void onTextReceived(String emisor, String text) {
                Toast.makeText(MapsActivity.this, "Emisor: "+emisor, Toast.LENGTH_SHORT).show();
                Log.d("contenido de mensaje",text);
                setBusMarker(text,map);
            }
        });

    }

    //evento que se dispara al dar click a un marcador
    @Override
    public boolean onMarkerClick(Marker marker) {
        tv_info_marker.setText("Nombre de la parada: "+ marker.getTitle());
        return true;
    }

    public void setMarkers(GoogleMap map)
    {

        //ciudad del conocimiento
        map.addMarker(new MarkerOptions().position(new LatLng(20.09309534136533, -98.71148228645326))
                .title("Ciudad del Conocimiento")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.096445519848682, -98.71409475803377))
                .title("CEVIDE")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.099160875085076, -98.70805978775026))
                .title("Segunda Entrada al Fraccionamiento Villas del Álamo")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.097417813869924, -98.7051147222519))
                .title("AV. Encino y esq. de Av. del Roble")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.096843506214125, -98.70368242263794))
                .title("Entronque Av. del Encino y de los Avellanos")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.097810759999764, -98.70231449604036))
                .title("De los Avellanos esq. Calle del Ébano")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.099599156210527, -98.70212674140932))
                .title("De los Avellanos esq. Calle Lima")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.101070159754418, -98.70157957077026))
            .title("Calle Tamarindo esq. Del Olmo")
            .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.103714908758747, -98.70448708534242))
                .title("Av.San Miguel Azoyatla esq. Calle Margarita")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));


        map.addMarker(new MarkerOptions().position(new LatLng(20.10845519467439, -98.71047377586366))
                .title("CEUNI")
                .icon(BitmapDescriptorFactory.fromResource(ICON_ESCUELA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.140928083252636, -98.80622863769533))
                .title("ICEA")
                .icon(BitmapDescriptorFactory.fromResource(ICON_ESCUELA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.136163598946613, -98.81307363510133))
                .title("ICSa_2")
                .icon(BitmapDescriptorFactory.fromResource(ICON_ESCUELA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.122362898745056, -98.7967336177826))
                .title("ICSHu")
                .icon(BitmapDescriptorFactory.fromResource(ICON_ESCUELA)));

        //------------------------------------------

        map.addMarker(new MarkerOptions().position(new LatLng(20.102440397197313, -98.76581311225891))
                .title("CENIHES")
                .icon(BitmapDescriptorFactory.fromResource(ICON_ESCUELA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.10851060630692, -98.77230405807497))
                .title("Puente de San Cayetano")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.11094869871367, -98.77496480941774))
                .title("Gran Foro")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.11223825289111, -98.77438545227052))
                .title("Aurrera")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.113427051233312, -98.77286195755006))
                .title("Entrada a Piracantos")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.11391062763547, -98.76968622207643))
                .title("semaforo palmar")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));


        map.addMarker(new MarkerOptions().position(new LatLng(20.114414351464987, -98.76623153686525))
                .title("esq_av_2_bonfil")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.11464606388193, -98.76271247863771))
                .title("esquia_bonfil")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.118151931613053, -98.76327037811281))
                .title("escuela_primaria")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.120106318017793, -98.76332938671113))
                .title("vias_del_tren")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.124397823085747, -98.76368880271912))
                .title("Oxxo")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.124669815916977, -98.75696182250978))
                .title("prepa_3")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.125782966832652, -98.74861478805543))
                .title("barranca_blanca")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.126422647257336, -98.74508500099184))
                .title("dos_caminos")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.126261468184374, -98.74053597450258))
                .title("maria_anaya")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));

        map.addMarker(new MarkerOptions().position(new LatLng(20.124689963515287, -98.73736023902894))
                .title("Manuel_fernando")
                .icon(BitmapDescriptorFactory.fromResource(ICON_PARADA)));





    }


    //este metodo dibuja las lineas del mapa
    public void loadPolylines(GoogleMap map)
    {
        Polyline polyline1 = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .color(Color.BLUE)
                .add(
                        new LatLng(20.09309534136571,-98.71146619319953),
                        new LatLng(20.092828331576,-98.71123552322398),
                        new LatLng(20.092732610974554,-98.71081709861765),
                        new LatLng(20.092657042037565,-98.71034502983133),
                        new LatLng(20.09260162479361,-98.7101411819459),
                        new LatLng(20.092566359264495,-98.70998561382328),
                        new LatLng(20.092515979923647,-98.70983004570017),
                        new LatLng(20.092410183255137,-98.7097281217576),
                        new LatLng(20.09242529706921,-98.70961546897928),
                        new LatLng(20.092531093727782,-98.70959937572519),
                        new LatLng(20.09261170065784,-98.70963156223308),
                        new LatLng(20.092742686829993,-98.70962083339701),
                        new LatLng(20.0935638668772,-98.70941698551202),
                        new LatLng(20.093956822677406,-98.70953500270855),
                        new LatLng(20.094893867145046,-98.70993733406087),
                        new LatLng(20.095055078531264,-98.71010363101972),
                        new LatLng(20.095055078531264,-98.71037721633921),
                        new LatLng(20.09511049490694,-98.71060252189649),
                        new LatLng(20.0952717060702,-98.71073126792919),
                        new LatLng(20.09543291706737,-98.7108063697816),
                        new LatLng(20.095730149408446,-98.71096193790449),
                        new LatLng(20.095876246454107,-98.71105849742902),
                        new LatLng(20.0959669273105,-98.71124625206004),
                        new LatLng(20.09612309977336,-98.71157884597788),
                        new LatLng(20.09638002832487,-98.71232986450207),
                        new LatLng(20.096586578423178,-98.71313452720668),
                        new LatLng(20.096601691834387,-98.71327400207531),
                        new LatLng(20.09651101134573,-98.71363878250132),
                        new LatLng(20.09640017956582,-98.71406793594372),
                        new LatLng(20.09616844013639,-98.7140572071079),
                        new LatLng(20.095886322107457,-98.71393918991099),
                        new LatLng(20.095599165734377,-98.71384263038647),
                        new LatLng(20.095513522503726,-98.7138694524769),
                        new LatLng(20.095382538648582,-98.71397137641918),
                        new LatLng(20.095327122369227,-98.71406257152567),
                        new LatLng(20.095276743916365,-98.71418595314051),
                        new LatLng(20.09558405222657,-98.71484577655802),
                        new LatLng(20.09621378048659,-98.7150925397874),
                        new LatLng(20.096531162569875,-98.71521592140209),
                        new LatLng(20.09668733446989,-98.71533393859873),
                        new LatLng(20.096863657395854,-98.71535539627115),
                        new LatLng(20.09703494233362,-98.71525347232829),
                        new LatLng(20.097523607155853,-98.71482968330423),
                        new LatLng(20.097649551450626,-98.71468484401713),
                        new LatLng(20.09784602434799,-98.71455073356654),
                        new LatLng(20.098767932343467,-98.7138158082966),
                        new LatLng(20.098919064283983,-98.71370851993572),
                        new LatLng(20.099060120630153,-98.71357977390302),
                        new LatLng(20.099322082078604,-98.71337592601787),
                        new LatLng(20.099589080796292,-98.71317207813303),
                        new LatLng(20.100289320553408,-98.71266245841991),
                        new LatLng(20.100465639423323,-98.71232450008404),
                        new LatLng(20.10027420749814,-98.71188461780584),
                        new LatLng(20.100223830636995,-98.71161639690409),
                        new LatLng(20.100385036536004,-98.71130526065856),
                        new LatLng(20.100707447835795,-98.71079027652777),
                        new LatLng(20.100485790138347,-98.71033966541302),
                        new LatLng(20.100213755262708,-98.70986759662657),
                        new LatLng(20.09967975950241,-98.70895564556133),
                        new LatLng(20.099483288905862,-98.70861768722536),
                        new LatLng(20.09898959247274,-98.70775938034059),
                        new LatLng(20.097891364212202,-98.70582818984987),
                        new LatLng(20.09732713385377,-98.7048625946045),
                        new LatLng(20.097055093490248,-98.70437979698181),
                        new LatLng(20.096798166046323,-98.70393991470338),
                        new LatLng(20.096949299887928,-98.70339274406435),
                        new LatLng(20.097029904543813,-98.70311915874481),
                        new LatLng(20.097120584732075,-98.70282411575319),
                        new LatLng(20.097402700537554,-98.70262563228609),
                        new LatLng(20.09784602434781,-98.70233058929443),
                        new LatLng(20.098566422862532,-98.7018531560898),
                        new LatLng(20.09898455474601,-98.70195508003235),
                        new LatLng(20.09919613912905,-98.70200872421266),
                        new LatLng(20.099568929965418,-98.70209991931915),
                        new LatLng(20.09978555125988,-98.70251834392549),
                        new LatLng(20.100430375664835,-98.7020891904831),
                        new LatLng(20.101110461026856,-98.7016385793686),
                        new LatLng(20.10144798377628,-98.70140254497528),
                        new LatLng(20.10177039288724,-98.70187461376192),
                        new LatLng(20.102233854820987,-98.70253443717958),
                        new LatLng(20.102556262313502,-98.70297968387605),
                        new LatLng(20.10283333072188,-98.70346248149873),
                        new LatLng(20.103175887348808,-98.70400428771973),
                        new LatLng(20.103614157234357,-98.70448172092439),
                        new LatLng(20.10386099835388,-98.70472311973572),
                        new LatLng(20.10417836493559,-98.70520055294038),
                        new LatLng(20.104354679425168,-98.70605349540712),
                        new LatLng(20.105246323945003,-98.70728731155397),
                        new LatLng(20.105508275042048,-98.70770037174225),
                        new LatLng(20.10589616336128,-98.70853185653687),
                        new LatLng(20.106339463120488,-98.70947062969208),
                        new LatLng(20.10646540032307,-98.70976567268373),
                        new LatLng(20.10691373594173,-98.71038258075716),
                        new LatLng(20.107346957903733,-98.71104776859285),
                        new LatLng(20.10755349353325,-98.71128916740419),
                        new LatLng(20.107714691883974,-98.71136426925659),
                        new LatLng(20.108006863471488,-98.71166467666627),
                        new LatLng(20.10797160141228,-98.7113803625107),
                        new LatLng(20.10818821107903,-98.71090292930604),
                        new LatLng(20.108399783022232,-98.7104630470276),
                        new LatLng(20.10850556888658,-98.71052742004396)));
        //acaba azul ----------------------------------------------------------------

        Polyline polyline2 = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .color(Color.GREEN)
                .add(
                        new LatLng(20.093130606775414,-98.71149301528938),
                        new LatLng(20.092838407425198,-98.71120333671576),
                        new LatLng(20.092712459261467,-98.71071517467506),
                        new LatLng(20.092682231687053,-98.71049523353584),
                        new LatLng(20.09264696617632,-98.71028602123265),
                        new LatLng(20.09253613166191,-98.7098890542985),
                        new LatLng(20.092505904053564,-98.70977640151982),
                        new LatLng(20.092420259131366,-98.70976567268376),
                        new LatLng(20.09241522119331,-98.70963692665106),
                        new LatLng(20.09251094198876,-98.7095940113068),
                        new LatLng(20.092591548928855,-98.70964229106909),
                        new LatLng(20.092727573046457,-98.70963692665106),
                        new LatLng(20.093019772603206,-98.70955109596254),
                        new LatLng(20.093573942679363,-98.70940625667576),
                        new LatLng(20.094238944182248,-98.70966374874125),
                        new LatLng(20.094919056435106,-98.70995879173286),
                        new LatLng(20.095055078531264,-98.71008753776552),
                        new LatLng(20.095060116384374,-98.71033430099493),
                        new LatLng(20.095080267795257,-98.71054887771614),
                        new LatLng(20.095191100509204,-98.710699081421),
                        new LatLng(20.09562435491102,-98.71087610721598),
                        new LatLng(20.095835943834377,-98.7110102176667),
                        new LatLng(20.095977002957767,-98.71123552322395),
                        new LatLng(20.09613317541077,-98.711605668068),
                        new LatLng(20.096274234266247,-98.71202409267433),
                        new LatLng(20.096601691834298,-98.71312379837042),
                        new LatLng(20.096596654030737,-98.71331691741953),
                        new LatLng(20.096395141755686,-98.71406793594367),
                        new LatLng(20.096178515770884,-98.71405184268959),
                        new LatLng(20.095548787369136,-98.71384799480448),
                        new LatLng(20.095337198057816,-98.7140142917634),
                        new LatLng(20.095286819608187,-98.71414303779609),
                        new LatLng(20.09533216021347,-98.71432006359107),
                        new LatLng(20.095402690018012,-98.71448636054997),
                        new LatLng(20.09556893871722,-98.71484041213996),
                        new LatLng(20.09656642720598,-98.71521592140203),
                        new LatLng(20.096747788066804,-98.71538221836096),
                        new LatLng(20.09694929988808,-98.71541440486918),
                        new LatLng(20.097095395796313,-98.715569972992),
                        new LatLng(20.097397662759985,-98.71569335460667),
                        new LatLng(20.098566422862696,-98.71634244918833),
                        new LatLng(20.099488326616576,-98.7169003486634),
                        new LatLng(20.099573967673432,-98.71697545051576),
                        new LatLng(20.100868653236766,-98.71774792671214),
                        new LatLng(20.102465585273595,-98.71861159801493),
                        new LatLng(20.10286859393874,-98.71872961521159),
                        new LatLng(20.104168289815885,-98.71934652328491),
                        new LatLng(20.105916313377577,-98.7200653553009),
                        new LatLng(20.106787799099912,-98.72046232223511),
                        new LatLng(20.10722605887219,-98.7206554412842),
                        new LatLng(20.10744770702535,-98.72075200080872),
                        new LatLng(20.10845015725227,-98.72132062911989),
                        new LatLng(20.109457638447815,-98.7216639518738),
                        new LatLng(20.111472581380042,-98.72258663177492),
                        new LatLng(20.111402058815575,-98.72481822967531),
                        new LatLng(20.111361760193045,-98.7259340286255),
                        new LatLng(20.111341610877883,-98.7264919281006),
                        new LatLng(20.111341610877883,-98.72670114040376),
                        new LatLng(20.111195528265377,-98.72703909873962),
                        new LatLng(20.110847951845972,-98.72768819332124),
                        new LatLng(20.110666607320653,-98.72808516025545),
                        new LatLng(20.1107018687726,-98.72848212718965),
                        new LatLng(20.110883213257043,-98.72880935668945),
                        new LatLng(20.11100914680315,-98.7291419506073),
                        new LatLng(20.111200565599116,-98.72945845127107),
                        new LatLng(20.111643850332825,-98.73006999492645),
                        new LatLng(20.11186549222874,-98.73037576675416),
                        new LatLng(20.112092171115737,-98.73068153858186),
                        new LatLng(20.112605975377083,-98.73120188713075),
                        new LatLng(20.11303918158053,-98.73205482959749),
                        new LatLng(20.11340690218412,-98.73276829719545),
                        new LatLng(20.113930776619803,-98.73387336730958),
                        new LatLng(20.114097005641426,-98.7344527244568),
                        new LatLng(20.11431360682883,-98.73517692089082),
                        new LatLng(20.114701473322093,-98.73657166957857),
                        new LatLng(20.114897924815452,-98.73726367950441),
                        new LatLng(20.11507926443651,-98.73780548572542),
                        new LatLng(20.115280752657817,-98.73831510543825),
                        new LatLng(20.115431868653484,-98.73841166496278),
                        new LatLng(20.115653505183104,-98.73776793479921),
                        new LatLng(20.116026256820206,-98.73710274696352),
                        new LatLng(20.116580345450117,-98.73631954193117),
                        new LatLng(20.117587774294368,-98.73522520065309),
                        new LatLng(20.118564974073983,-98.7346136569977),
                        new LatLng(20.119884687797526,-98.73401284217834),
                        new LatLng(20.12072083653128,-98.73381972312929),
                        new LatLng(20.121023057865262,-98.73375535011293),
                        new LatLng(20.12162749878109,-98.73439908027649),
                        new LatLng(20.122312528994833,-98.73496770858766),
                        new LatLng(20.122695338689613,-98.73527884483339),
                        new LatLng(20.12299755620777,-98.73537540435792),
                        new LatLng(20.123309847029574,-98.73545050621033),
                        new LatLng(20.12351132464813,-98.73628735542299),
                        new LatLng(20.123632211094655,-98.73656630516054),
                        new LatLng(20.12417619894753,-98.73694181442262),
                        new LatLng(20.125244021451344,-98.73778939247133),
                        new LatLng(20.12576785623981,-98.73820781707764),
                        new LatLng(20.126342057741617,-98.73863697052003),
                        new LatLng(20.126211099689996,-98.73936653137208),
                        new LatLng(20.126201025989175,-98.74014973640443),
                        new LatLng(20.126251394486797,-98.74136209487915),
                        new LatLng(20.126382352504667,-98.74453783035278),
                        new LatLng(20.12628161557758,-98.74484896659851),
                        new LatLng(20.126221173390167,-98.74539613723756),
                        new LatLng(20.126009625550225,-98.74665141105653),
                        new LatLng(20.12572756131838,-98.74835729598999),
                        new LatLng(20.12574267191513,-98.7488454580307),
                        new LatLng(20.125415275325285,-98.75080883502962),
                        new LatLng(20.125188615746087,-98.75225186347963),
                        new LatLng(20.125092914936225,-98.75289559364319),
                        new LatLng(20.125052619840858,-98.75358760356904),
                        new LatLng(20.124997214067776,-98.75437617301942),
                        new LatLng(20.124896476248235,-98.75591576099397),
                        new LatLng(20.124851144208275,-98.75672042369844),
                        new LatLng(20.124846107314138,-98.75731050968172),
                        new LatLng(20.124825959735954,-98.75858187675477),
                        new LatLng(20.124820922841003,-98.76046478748323),
                        new LatLng(20.124710111111003,-98.7638282775879),
                        new LatLng(20.12297740839139,-98.76365661621095),
                        new LatLng(20.122111049831705,-98.76352787017824),
                        new LatLng(20.12127490853077,-98.76344203948976),
                        new LatLng(20.120690614365763,-98.76335620880127),
                        new LatLng(20.120116392111246,-98.76334547996521),
                        new LatLng(20.117567625781074,-98.76294851303102),
                        new LatLng(20.116519899513136,-98.76275539398195),
                        new LatLng(20.114615840542665,-98.76251935958864),
                        new LatLng(20.11445464930128,-98.76384973526002),
                        new LatLng(20.11364869060318,-98.76998662948608),
                        new LatLng(20.113245709697168,-98.77305507659914),
                        new LatLng(20.11278228037207,-98.7764883041382),
                        new LatLng(20.111976313058967,-98.77562999725343),
                        new LatLng(20.110263618734198,-98.77382755279541),
                        new LatLng(20.109397189758983,-98.77292633056642),
                        new LatLng(20.106112775900492,-98.76957893371583),
                        new LatLng(20.10450076842322,-98.76779794692995),
                        new LatLng(20.10264190169208,-98.7658613920212)

                )); //acaba verde------------------------------------

        Polyline polyline3 = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .color(Color.RED)
                .add(
                        new LatLng(20.140882755862442,-98.80607843399058),
                        new LatLng(20.140761882757584,-98.80558490753184),
                        new LatLng(20.140459699586145,-98.80555272102366),
                        new LatLng(20.1400517513776,-98.80632519721995),
                        new LatLng(20.140268316608733,-98.80740880966198),
                        new LatLng(20.140474808758643,-98.80792915821085),
                        new LatLng(20.140056787781727,-98.80825638771068),
                        new LatLng(20.14015751583029,-98.80868554115305),
                        new LatLng(20.139341616769777,-98.80869626998911),
                        new LatLng(20.137165865111605,-98.80877137184153),
                        new LatLng(20.136631995492262,-98.80877137184153),
                        new LatLng(20.136138416296927,-98.80866408348093),
                        new LatLng(20.135856370342548,-98.80900740623484),
                        new LatLng(20.135574323879013,-98.8093614578248),
                        new LatLng(20.135544104584863,-98.81031632423414),
                        new LatLng(20.135554177683645,-98.81187200546276),
                        new LatLng(20.13551388528484,-98.81311655044566),
                        new LatLng(20.135997393383363,-98.81311655044566),
                        new LatLng(20.13647082694682,-98.81311655044566),
                        new LatLng(20.13650104606167,-98.81195783615124),
                        new LatLng(20.136511119098735,-98.81083130836497),
                        new LatLng(20.136511119098735,-98.8099730014802),
                        new LatLng(20.13647082694682,-98.80923271179209),
                        new LatLng(20.13594702802627,-98.80905032157908),
                        new LatLng(20.13542322734983,-98.80887866020214),
                        new LatLng(20.134889351776827,-98.80899667739878),
                        new LatLng(20.134516645107034,-98.80914688110359),
                        new LatLng(20.13399283963621,-98.80966186523445),
                        new LatLng(20.13367049693545,-98.8099193572999),
                        new LatLng(20.133237347884144,-98.80999445915232),
                        new LatLng(20.132884783816834,-98.81021976470957),
                        new LatLng(20.132381119483714,-98.81057381629954),
                        new LatLng(20.129439687361966,-98.81275177001963),
                        new LatLng(20.128311452135257,-98.81240844726572),
                        new LatLng(20.12714291420929,-98.811378479004),
                        new LatLng(20.125087878050014,-98.81167888641363),
                        new LatLng(20.1240905713556,-98.81187200546276),
                        new LatLng(20.123904205257656,-98.81195783615124),
                        new LatLng(20.123823614443925,-98.81219387054453),
                        new LatLng(20.12387902063286,-98.81240844726572),
                        new LatLng(20.124080497518083,-98.81253182888042),
                        new LatLng(20.124362564721153,-98.81246745586405),
                        new LatLng(20.124478413603647,-98.8122957944871),
                        new LatLng(20.124594262400347,-98.81212413311015),
                        new LatLng(20.124956918947866,-98.81131947040568),
                        new LatLng(20.12510802559442,-98.81091177463541),
                        new LatLng(20.1254908284454,-98.80998373031626),
                        new LatLng(20.12607510467416,-98.80870699882517),
                        new LatLng(20.12667952606146,-98.80708694458018),
                        new LatLng(20.126357168278993,-98.80552053451548),
                        new LatLng(20.125531123427756,-98.80421161651621),
                        new LatLng(20.124423007627087,-98.80275249481211),
                        new LatLng(20.123697691214257,-98.80137920379649),
                        new LatLng(20.12335517951644,-98.79983425140391),
                        new LatLng(20.123012667068416,-98.79854679107676),
                        new LatLng(20.122911927970392,-98.79743099212656),
                        new LatLng(20.122730597430163,-98.79663705825813),
                        new LatLng(20.12267015387019,-98.79603624343882),
                        new LatLng(20.122911927970392,-98.79519939422617),
                        new LatLng(20.122811188807308,-98.79333257675182),
                        new LatLng(20.122388083614275,-98.78970623016367),
                        new LatLng(20.12186423750315,-98.78760337829597),
                        new LatLng(20.119930021277337,-98.78464221954357),
                        new LatLng(20.118156968722868,-98.78262519836433),
                        new LatLng(20.11569883988921,-98.77953529357917),
                        new LatLng(20.11345223754122,-98.7771910429002),
                        new LatLng(20.112948510614007,-98.77713739871994),
                        new LatLng(20.112585826222194,-98.77730369567881),
                        new LatLng(20.111588439809825,-98.77764701843272),
                        new LatLng(20.10934177844039,-98.77833902835856),
                        new LatLng(20.10821843565995,-98.77873599529276),
                        new LatLng(20.107568605885717,-98.77893447875987),
                        new LatLng(20.107115234677927,-98.77910077571879),
                        new LatLng(20.106802911526373,-98.77918124198924),
                        new LatLng(20.10500452254333,-98.77971768379221),
                        new LatLng(20.104102801522178,-98.77999126911173),
                        new LatLng(20.103624232389837,-98.78014147281657),
                        new LatLng(20.103150699387154,-98.78036141395579),
                        new LatLng(20.102344682471543,-98.78075301647196),
                        new LatLng(20.10154369905039,-98.7811017036439),
                        new LatLng(20.1005260915612,-98.7816059589387),
                        new LatLng(20.09955381684066,-98.78207266330729),
                        new LatLng(20.099306968929916,-98.78222286701212),
                        new LatLng(20.099075233802413,-98.7824159860612),
                        new LatLng(20.09900974337809,-98.78266274929057),
                        new LatLng(20.09884349833189,-98.78277003765116),
                        new LatLng(20.098692366318545,-98.78277540206919),
                        new LatLng(20.098249044904374,-98.78311336040507),
                        new LatLng(20.09803242148418,-98.78328502178202),
                        new LatLng(20.09755383379572,-98.78359079360969),
                        new LatLng(20.097075244644508,-98.78384828567509),
                        new LatLng(20.09373515542397,-98.78524303436289),
                        new LatLng(20.09260666272542,-98.7856936454773),
                        new LatLng(20.0922137035379,-98.78582775592804),
                        new LatLng(20.091966844056948,-98.78585994243622),
                        new LatLng(20.09171494622637,-98.78586530685426),
                        new LatLng(20.091447934083664,-98.78587603569032),
                        new LatLng(20.091251453159664,-98.78586530685426),
                        new LatLng(20.090425222987225,-98.78587067127228),
                        new LatLng(20.089866003751197,-98.78585994243622),
                        new LatLng(20.089195945641215,-98.78587603569032),
                        new LatLng(20.08856618916588,-98.78588140010834),
                        new LatLng(20.087881011245287,-98.7858921289444),
                        new LatLng(20.087603916382317,-98.78588140010834),
                        new LatLng(20.086636599564624,-98.78591358661653),
                        new LatLng(20.086168053334095,-98.78596723079683),
                        new LatLng(20.085936298766686,-98.78599405288698),
                        new LatLng(20.085704543856526,-98.78602623939521),
                        new LatLng(20.085190651311123,-98.78614425659181),
                        new LatLng(20.084253548801385,-98.78484606742869),
                        new LatLng(20.081079450278025,-98.78477096557624)
                ));






    }//acaba loadPolyline


    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SMS_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Error, necesito los permisos", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void setBusMarker(String coordenadas,GoogleMap map)
    {

        try {
            if(bus_pos != null)
            {
                bus_pos.remove();
            }

            String[] coord_sep = coordenadas.split(",",2);
            Double lat = Double.valueOf(coord_sep[0]);
            Double longt = Double.valueOf(coord_sep[1]);

            LatLng pos = new LatLng(lat,longt);

            bus_pos = map.addMarker(new MarkerOptions().position(pos)
                    .title("Ruta 1")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon)));

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,15.0f));

        } catch (ArrayIndexOutOfBoundsException ex)
        {
            ex.printStackTrace();
            Log.e(TAG,"Error al obtener coordenadas: " + ex.getMessage());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            Log.e(TAG,"Error al obtener coordenadas: " + e.getMessage());
        }



      /*  map.addMarker(new MarkerOptions().position(pos)
                .title("Ruta 1")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon)));
      */

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(receiver,filter);
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
        unregisterReceiver(receiver);
        Log.d(TAG, "onStop: el brodtca");
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



}
