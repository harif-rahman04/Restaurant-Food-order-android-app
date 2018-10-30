package com.opus.restaurant.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.opus.restaurant.Models.AddonMaster;
import com.opus.restaurant.Models.AddonOptionalCell;
import com.opus.restaurant.Models.AddonSectionCell;
import com.opus.restaurant.Models.CartItemModel;
import com.opus.restaurant.Models.Category;
import com.opus.restaurant.Models.Discount;
import com.opus.restaurant.Models.EditTablesDetails;
import com.opus.restaurant.Models.Ip;
import com.opus.restaurant.Models.Items;
import com.opus.restaurant.Models.KotAddonList;
import com.opus.restaurant.Models.KotAddons;
import com.opus.restaurant.Models.KotCount;
import com.opus.restaurant.Models.KotNoListModel;
import com.opus.restaurant.Models.KotOrders;
import com.opus.restaurant.Models.KotOrdersView;
import com.opus.restaurant.Models.KotTables;
import com.opus.restaurant.Models.Sales;
import com.opus.restaurant.Models.Tables;
import com.opus.restaurant.Models.Waiters;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    // DATABASE VERSION
    private static final int DATABASE_VERSION = 1;
    // DATABASE NAME
    private static final String DATABASE_NAME = "midway";

    // TABLE NAMES
    private static final String TABLE_WAITER_MASTER           = "WaiterMaster";
    private static final String TABLE_TABLE_MASTER            = "TableMaster";
    private static final String TABLE_ITEM_MASTER             = "itemmaster";
    private static final String TABLE_CATEGORY_MASTER         = "categorymaster";
    private static final String TABLE_ADDONS_MASTER           = "addonmaster";
    private static final String TABLE_IP_DETAILS              = "ipmaster";
    private static final String TABLE_KOT_COUNT               = "kotlist";
    private static final String TABLE_DISCOUNT_MASTER         = "discountmaster";
    private static final String TABLE_KOT_ORDER               = "kotorder";
    private static final String TABLE_KOT_ADDON_ORDER         = "kotaddon";
    private static final String TABLE_SALES_DETAILS           = "sales";
    private static final String TABLE_KOT_SERVER              = "kot";
    private static final String TABLE_KOT_ORDER_TYPE             = "ordertype";
    private static final String TABLE_KOT_SAMPLE_ORDER         =  "samplekot";
    private static final String TABLE_ADDON_SAMPLE_ORDER       =  "sampleaddon";

    // WAITER MASTER FIELDS
    private static final String KEY_WAITER_AUTO_ID            = "Id";
    private static final String KEY_WAITER_CODE               = "WaiterCode";
    private static final String KEY_WAITER_NAME               = "WaiterName";
    private static final String KEY_WAITER_MOBILE             = "MobileNo";
    private static final String KEY_WAITER_IDPROOF            = "IdProof";
    private static final String KEY_WAITER_IDPROOF_NO         = "ProofNo";
    private static final String KEY_WAITER_MODIFIED_TYPE      = "ModifiedType";

    // TABLE MASTER FIELDS
    private static final String KEY_TABLE_AUTO_ID             = "id";
    private static final String KEY_TABLE_ID                  = "tableid";
    private static final String KEY_TABLE_FLOOR               = "floor";
    private static final String KEY_TABLE_CODE                = "tablecode";
    private static final String KEY_TABLE_NAME                = "tablename";
    private static final String KEY_TABLE_POSITION            = "tableposition";
    private static final String KEY_TABLE_CHAIRS              = "chairs";
    private static final String KEY_TABLE_MODIFIEDTYPE        = "modifiedtype";
    private static final String KEY_TABLE_TYPE        = "type";

    // CATEGORY MASTER FIELDS
    private static final String KEY_CATEGORY_AUTO_ID          = "id";
    private static final String KEY_CATEGORY_ID               =  "categoryid";
    private static final String KEY_CATEGORY_NAME             = "category";
    private static final String KEY_CATEGORY_TYPE             = "type";
    private static final String KEY_CATEGORY_MODIFIEDTYPE     = "modifiedtype";

    // ITEM MASTER TABLE FIELDS
    private static final String KEY_ID                        = "id";
    private static final String KEY_ITEM_ID                   = "itemid";
    private static final String KEY_ITEM_CODE                 = "itemcode";
    private static final String KEY_ITEM_NAME                 = "itemname";
    private static final String KEY_ITEM_COST                 = "cost";
    private static final String KEY_ITEM_CATEGORY             = "category";
    private static final String KEY_ITEM_DATE                 = "date";
    private static final String KEY_ITEM_DEL                  = "modifiedtype";
    private static final String KEY_ITEM_DESCRIPTION          = "description";
    private static final String KEY_ITEM_TYPE                 = "type"; // Alcohol Type
    private static final String KEY_ITEM_KOTTYPE              = "kottype"; // Kot Type
    private static final String KEY_ITEM_NORMALRATE            = "normalrate"; // Kot Type
    private static final String KEY_ITEM_ACRATE                = "acrate"; // Kot Type


    // DISCOUNT MASTER
    private static final String KEY_DISCOUNT_AUTO_ID          = "id";
    private static final String KEY_DISCOUNT_ID               = "discountid";
    private static final String KEY_DISCOUNT_DATE             = "date";
    private static final String KEY_DISCOUNT_TYPE             = "discounttype";
    private static final String KEY_DISCOUNT_AMOUNT           = "amount";
    private static final String KEY_DISCOUNT_MODIFIEDTYPE     = "modifiedtype";
    private static final String KEY_DISCOUNT_DESCRIPTION      = "description";

    // IP DETAILS_TABLE FIELDS
    private static final String KEY_IP_AUTOID           = "id";
    private static final String KEY_IP_NO               = "ipno";

    // ADDONS MASTER FIELDS
    private static final String KEY_ADDON_AUTO_ID       = "addonid";
    private static final String KEY_ADDON_ID            = "id";
    private static final String KEY_ADDON_DATE          = "date";
    private static final String KEY_ADDON_ITEMNAME      = "itemname";
    private static final String KEY_ADDON_PRIORITY      = "priority";
    private static final String KEY_ADDON_GROUPNAME     = "groupname";
    private static final String KEY_ADDON_NAME          = "name";
    private static final String KEY_ADDON_AMOUNT        = "amount";
    private static final String KEY_ADDON_TYPE          = "type";
    private static final String KEY_ADDON_ITEMTYPE      = "itype";
    private static final String KEY_ADDON_MODIFIEDTYPE  = "modifiedtype";
    private static final String KEY_ADDON_KOTTYPE       = "kottype";  // KotType ( Dinein or Takeaway )

    // KOT_NO_SERVER_TABLE FIELDS
    private static final String KEY_SERVER_AUTOID        = "id";
    private static final String KEY_SERVER_LOCID         = "locid";
    private static final String KEY_SERVER_KOTNO         = "kotno";
    private static final String KEY_SERVER_KOT_STATUS    = "status";

    // KOT_COUNT_TABLE
    private static final String KEY_KOT_COUNT_ID          = "id";
    private static final String KEY_KOT_COUNT_KOTNO       = "kotno";
    private static final String KEY_KOT_COUNT_CREATED_ON  = "createdon";
    private static final String KEY_KOT_COUNT_STATUS      = "status";

    // KOT ORDER TABLE
    private static final String KEY_KOT_ORDER_AUTO_ID           ="id";
    private static final String KEY_KOT_ORDER_TABLE_NAME        = "tablename";
    private static final String KEY_KOT_ORDER_KOT               = "kot";
    private static final String KEY_KOT_ORDER_WAITERCODE        = "waitercode";
    private static final String KEY_KOT_ORDER_NO_OF_PERSON      = "noofperson";
    private static final String KEY_KOT_ORDER_ITEM_CODES        = "itemcodes";
    private static final String KEY_KOT_ORDER_ITEM_NAME         = "itemname";
    private static final String KEY_KOT_ORDER_ITEM_RATE         = "itemrate";
    private static final String KEY_KOT_ORDER_ADDON_AMOUNT      = "addonamount";
    private static final String KEY_KOT_ORDER_QTY               = "qty";
    private static final String KEY_KOT_ORDER_AMOUNT            = "amount";
    private static final String KEY_KOT_ORDER_STARTED_AT        = "startedat";
    private static final String KEY_KOT_ORDER_FINISHED_AT       = "finishedat";
    private static final String KEY_KOT_ORDER_INVOICE_NO        = "invoiceno";
    private static final String KEY_KOT_ORDER_MODIFIED_DATE     = "modifieddate";
    private static final String KEY_KOT_ORDER_MODIFIED_TYPE     = "modifiedtype";
    private static final String KEY_KOT_ORDER_ROOM_TYPE         = "roomtype";
    private static final String KEY_KOT_ORDER_SUGGESTION        = "suggestion";
    private static final String KEY_KOT_ORDER_LOCAL_ID          = "localid";

    // KOT ORDER ADDONS
    private static final String KEY_KOT_ADDON_AUTO_ID           = "id";
    private static final String KEY_KOT_ADDON_KOTNO             = "kotno";
    private static final String KEY_KOT_ADDON_ITEM_CODE         = "itemcode";
    private static final String KEY_KOT_ADDON_ADDON             = "addon";
    private static final String KEY_KOT_ADDON_RATE              = "rate";
    private static final String KEY_KOT_ADDON_QTY               = "qty";
    private static final String KEY_KOT_ADDON_AMOUNT            = "amount";
    private static final String KEY_KOT_ADDON_STATUS            = "status";
    private static final String KEY_KOT_ADDON_LOCAL_ID          = "localid";

    // Sales Details
    private static final String KEY_SALES_AUTO_ID               = "id";
    private static final String KEY_SALES_LOCAL_ID              = "lccalid";
    private static final String KEY_SALES_KOTNO                 = "kotno";
    private static final String KEY_SALES_INVOICE_DATE          = "invoicedate";
    private static final String KEY_SALES_INVOICE_NO            = "invoiceno";
    private static final String KEY_SALES_TABLE_NO              = "tableno";
    private static final String KEY_SALES_NO_OF_PERSONS         = "noofpersons";
    private static final String KEY_SALES_BILL_AMOUNT           = "billamount";
    private static final String KEY_SALES_GST_AMOUNT            = "gst";
    private static final String KEY_SALES_EXTRA_CHARGES         = "extracharges";
    private static final String KEY_SALES_TOTAL_AMOUNT          = "totalamount";
    private static final String KEY_SALES_DISCOUNT              = "discount";
    private static final String KEY_SALES_PAYABLE_AMOUNT        = "payableamount";
    private static final String KEY_SALES_GIVEN_AMOUNT          = "givenamount";
    private static final String KEY_SALES_BALANCE               = "balance";
    private static final String KEY_SALES_WAITER                = "waiter";
    private static final String KEY_SALES_MODIFIED_BY           = "modifiedby";
    private static final String KEY_SALES_MODIFIED_DATE         = "modifieddate";

    private final ArrayList<Tables> table_list = new ArrayList<Tables>();
    private final ArrayList<Items> item_list = new ArrayList<Items>();
    private final ArrayList<AddonMaster> addon_master_list = new ArrayList<AddonMaster>();
    private final ArrayList<Category> category_list = new ArrayList<Category>();
    private final ArrayList<AddonSectionCell> adon_list = new ArrayList<AddonSectionCell>();
    private final ArrayList<AddonOptionalCell> addon_optional_list = new ArrayList<AddonOptionalCell>();
    private final ArrayList<KotCount> kotcount_list = new ArrayList<KotCount>();
    private final ArrayList<KotOrdersView> kot_view_list = new ArrayList<KotOrdersView>();
    private final ArrayList<KotAddonList> kot_addon_list = new ArrayList<KotAddonList>();
    private final ArrayList<KotOrders> kot_order_addon_list = new ArrayList<KotOrders>();
    private final ArrayList<KotOrders> kot_details_list = new ArrayList<KotOrders>();
    private final ArrayList<Sales>sales_details_list = new ArrayList<Sales>();
    private final ArrayList<KotNoListModel> kot_no_list = new ArrayList<KotNoListModel>();
    private final ArrayList<CartItemModel> cart_item_list = new ArrayList<CartItemModel>();
    private final ArrayList<EditTablesDetails> edit_table_list = new ArrayList<EditTablesDetails>();

    ArrayList<AddonMaster> adon_master_list;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  }

    // Creating ITEM MASTER TABLE
    @Override
    public void onCreate(SQLiteDatabase db) {
        // WAITER MASTER TABLE CREATION
        String CREATE_WAITER_MASTER     = "CREATE TABLE " + TABLE_WAITER_MASTER + "("
                + KEY_WAITER_AUTO_ID        +   " INTEGER PRIMARY KEY,"
                + KEY_WAITER_CODE           +   " TEXT,"
                + KEY_WAITER_NAME           +   " TEXT,"
                + KEY_WAITER_MOBILE         +   " TEXT,"
                + KEY_WAITER_IDPROOF        +   " TEXT,"
                + KEY_WAITER_IDPROOF_NO     +   " TEXT,"
                + KEY_WAITER_MODIFIED_TYPE  +   " TEXT" + ")";

        //TABLE MASTER TABLE CREATION
        String CREATE_TABLE_MASTER      = "CREATE TABLE " + TABLE_TABLE_MASTER + "("
                + KEY_TABLE_AUTO_ID         +   " INTEGER PRIMARY KEY,"
                + KEY_TABLE_ID              +   " TEXT,"
                + KEY_TABLE_CODE            +   " TEXT,"
                + KEY_TABLE_FLOOR           +   " TEXT,"
                + KEY_TABLE_NAME            +   " TEXT,"
                + KEY_TABLE_POSITION        +   " TEXT,"
                + KEY_TABLE_CHAIRS          +   " TEXT,"
                + KEY_TABLE_MODIFIEDTYPE          +   " TEXT,"
                + KEY_TABLE_TYPE    +   " TEXT" + ")";

        // CATEGORY MASTER TABLE CREATION
        String CREATE_CATEGORY_TABLE    = "CREATE TABLE " + TABLE_CATEGORY_MASTER + "("
                + KEY_CATEGORY_AUTO_ID      +   " INTEGER PRIMARY KEY,"
                + KEY_CATEGORY_ID           +   " TEXT,"
                + KEY_CATEGORY_NAME         +   " TEXT,"
                + KEY_CATEGORY_TYPE         +   " TEXT,"
                + KEY_CATEGORY_MODIFIEDTYPE +   " TEXT" + ")";

        // ITEM MASTER TABLE CREATION
        String CREATE_ITEM_MASTER_TABLE = "CREATE TABLE " + TABLE_ITEM_MASTER + "("
                + KEY_ID                +   " INTEGER PRIMARY KEY,"
                + KEY_ITEM_ID           +   " TEXT,"
                + KEY_ITEM_DATE         +   " TEXT,"
                + KEY_ITEM_CODE         +   " TEXT,"
                + KEY_ITEM_NAME         +   " TEXT,"
                + KEY_ITEM_COST         +   " TEXT,"
                + KEY_ITEM_CATEGORY     +   " TEXT,"
                + KEY_ITEM_DESCRIPTION  +   " TEXT,"
                + KEY_ITEM_TYPE         +   " TEXT,"
                + KEY_ITEM_DEL          +   " TEXT,"
                + KEY_ITEM_KOTTYPE      +   " TEXT,"
                + KEY_ITEM_NORMALRATE   +   " TEXT,"
                + KEY_ITEM_ACRATE       +   " TEXT" + ")";

        // DISCOUNT MASTER TABLE CREATION
        String CREATE_DISCOUT_MASTER_TABLE = "CREATE TABLE " + TABLE_DISCOUNT_MASTER + "("
                + KEY_DISCOUNT_AUTO_ID      + " INTEGER PRIMARY KEY,"
                + KEY_DISCOUNT_ID           + " TEXT,"
                + KEY_DISCOUNT_DATE         + " TEXT,"
                + KEY_DISCOUNT_TYPE         + " TEXT,"
                + KEY_DISCOUNT_DESCRIPTION  + " TEXT,"
                + KEY_DISCOUNT_AMOUNT       + " TEXT,"
                + KEY_DISCOUNT_MODIFIEDTYPE + " TEXT" + ")";

        // ADDON MASTER TABLE CREATION
        String CREATE_ADDON_MASTER      = "CREATE TABLE " + TABLE_ADDONS_MASTER + "("
                + KEY_ADDON_AUTO_ID     +   " INTEGER PRIMARY KEY,"
                + KEY_ADDON_ID          +   " TEXT,"
                + KEY_ADDON_DATE        +   " TEXT,"
                + KEY_ADDON_ITEMNAME    +   " TEXT,"
                + KEY_ADDON_PRIORITY    +   " TEXT,"
                + KEY_ADDON_GROUPNAME   +   " TEXT,"
                + KEY_ADDON_NAME        +   " TEXT,"
                + KEY_ADDON_AMOUNT      +   " TEXT,"
                + KEY_ADDON_TYPE        +   " TEXT,"
                + KEY_ADDON_ITEMTYPE    +   " TEXT,"
                + KEY_ADDON_MODIFIEDTYPE+   " TEXT,"
                + KEY_ADDON_KOTTYPE     +   " TEXT" + ")";

        // IP DETAILS TABLE
        String CREATE_IP_DETAILS        = "CREATE TABLE " + TABLE_IP_DETAILS + "("
                + KEY_IP_AUTOID         +   " INTEGER PRIMARY KEY,"
                + KEY_IP_NO             +   " TEXT" + ")";

        // KOT COUNT TABLE
        String CREATE_KOT_COUNT        = "CREATE TABLE " + TABLE_KOT_COUNT + "("
                + KEY_KOT_COUNT_ID            +   " INTEGER PRIMARY KEY,"
                + KEY_KOT_COUNT_KOTNO         +   " TEXT,"
                + KEY_KOT_COUNT_CREATED_ON    +   " TEXT,"
                + KEY_KOT_COUNT_STATUS        +   " TEXT" + ")";

        String CREATE_KOT_ORDER =  "CREATE TABLE " + TABLE_KOT_ORDER + "("
                + KEY_KOT_ORDER_AUTO_ID        + " INTEGER PRIMARY KEY,"
                + KEY_KOT_ORDER_LOCAL_ID       + " TEXT,"
                + KEY_KOT_ORDER_TABLE_NAME     + "  TEXT,"
                + KEY_KOT_ORDER_KOT            + "  TEXT,"
                + KEY_KOT_ORDER_WAITERCODE     + "  TEXT,"
                + KEY_KOT_ORDER_NO_OF_PERSON   + "  TEXT,"
                + KEY_KOT_ORDER_ITEM_CODES     + "  TEXT,"
                + KEY_KOT_ORDER_ITEM_NAME      + "  TEXT,"
                + KEY_KOT_ORDER_ITEM_RATE      + "  TEXT,"
                + KEY_KOT_ORDER_ADDON_AMOUNT   + "  TEXT,"
                + KEY_KOT_ORDER_QTY            + "  TEXT,"
                + KEY_KOT_ORDER_AMOUNT         + "  TEXT,"
                + KEY_KOT_ORDER_STARTED_AT     + "  TEXT,"
                + KEY_KOT_ORDER_FINISHED_AT    + "  TEXT,"
                + KEY_KOT_ORDER_INVOICE_NO     + "  TEXT,"
                + KEY_KOT_ORDER_MODIFIED_DATE  + "  TEXT,"
                + KEY_KOT_ORDER_MODIFIED_TYPE  + "  TEXT,"
                + KEY_KOT_ORDER_ROOM_TYPE      + "  TEXT,"
                + KEY_KOT_ORDER_SUGGESTION     + "  TEXT,"
                + TABLE_KOT_ORDER_TYPE         +" TEXT"+")";

        String CREATE_KOT_ADDON_ORDER = "CREATE TABLE " + TABLE_KOT_ADDON_ORDER + "("
                +KEY_KOT_ADDON_AUTO_ID      + " INTEGER PRIMARY KEY,"
                +KEY_KOT_ADDON_LOCAL_ID     + " TEXT,"
                +KEY_KOT_ADDON_KOTNO        + " TEXT,"
                +KEY_KOT_ADDON_ITEM_CODE    + " TEXT,"
                +KEY_KOT_ADDON_ADDON        + " TEXT,"
                +KEY_KOT_ADDON_RATE         + " TEXT,"
                +KEY_KOT_ADDON_QTY          + " TEXT,"
                +KEY_KOT_ADDON_AMOUNT       + " TEXT,"
                +KEY_KOT_ADDON_STATUS       + " TEXT" + ")";

        String CREATE_KOT_SALES = "CREATE TABLE "+ TABLE_SALES_DETAILS + "("
                + KEY_SALES_AUTO_ID         + " INTEGER PRIMARY KEY,"
                + KEY_SALES_LOCAL_ID        + " TEXT,"
                + KEY_SALES_KOTNO           + " TEXT,"
                + KEY_SALES_INVOICE_DATE    + " TEXT,"
                + KEY_SALES_INVOICE_NO      + " TEXT,"
                + KEY_SALES_TABLE_NO        + " TEXT,"
                + KEY_SALES_NO_OF_PERSONS   + " TEXT,"
                + KEY_SALES_BILL_AMOUNT     + " TEXT,"
                + KEY_SALES_GST_AMOUNT      + " TEXT,"
                + KEY_SALES_EXTRA_CHARGES   + " TEXT,"
                + KEY_SALES_TOTAL_AMOUNT    + " TEXT,"
                + KEY_SALES_DISCOUNT        + " TEXT,"
                + KEY_SALES_PAYABLE_AMOUNT  + " TEXT,"
                + KEY_SALES_GIVEN_AMOUNT    + " TEXT,"
                + KEY_SALES_BALANCE         + " TEXT,"
                + KEY_SALES_WAITER          + " TEXT,"
                + KEY_SALES_MODIFIED_BY     + " TEXT,"
                + KEY_SALES_MODIFIED_DATE   + " TEXT" + ")";

        String CREATE_SERVER_KOT_DETAILS = "CREATE TABLE " + TABLE_KOT_SERVER + "("
                + KEY_SERVER_AUTOID + " INTEGER PRIMARY KEY,"
                + KEY_SERVER_KOTNO + " TEXT," + KEY_SERVER_LOCID + " TEXT," + KEY_SERVER_KOT_STATUS + " TEXT" + ")";

        db.execSQL(CREATE_WAITER_MASTER);
        db.execSQL(CREATE_TABLE_MASTER);
        db.execSQL(CREATE_ITEM_MASTER_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_DISCOUT_MASTER_TABLE);
        db.execSQL(CREATE_ADDON_MASTER);
        db.execSQL(CREATE_IP_DETAILS);
        db.execSQL(CREATE_KOT_COUNT);
        db.execSQL(CREATE_KOT_ORDER);
        db.execSQL(CREATE_KOT_ADDON_ORDER);
        db.execSQL(CREATE_KOT_SALES);
        db.execSQL(CREATE_SERVER_KOT_DETAILS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDONS_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TABLE_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAITER_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IP_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KOT_COUNT);
        // Create tables again
        onCreate(db);
    }

    public void Add_Waitermaster(Waiters waiters){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WAITER_CODE,         waiters.getWaiterCode());
        values.put(KEY_WAITER_NAME,         waiters.getWaiterName());
        values.put(KEY_WAITER_MOBILE,       waiters.getMobileNo());
        values.put(KEY_WAITER_IDPROOF,      waiters.getIdProof());
        values.put(KEY_WAITER_IDPROOF_NO,   waiters.getIdProofNo());
        values.put(KEY_WAITER_MODIFIED_TYPE,waiters.getModifiedType());
        db.insert(TABLE_WAITER_MASTER, null, values);
        db.close();
    }

    public void Add_Tablemaster(Tables tables){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TABLE_ID,           tables.getTableid());
        values.put(KEY_TABLE_CODE,         tables.getTableCode());
        values.put(KEY_TABLE_FLOOR,        tables.getFloor());
        values.put(KEY_TABLE_NAME,         tables.getTableName());
        values.put(KEY_TABLE_POSITION,     tables.getTablePosition());
        values.put(KEY_TABLE_CHAIRS,       tables.getChairsCount());
        values.put(KEY_TABLE_MODIFIEDTYPE, tables.getModifiedType());
        values.put(KEY_TABLE_TYPE, tables.getType());
        db.insert(TABLE_TABLE_MASTER, null, values);
        db.close();
    }

    public void Add_Itemaster(Items items){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_ITEM_ID, items.getId());
            values.put(KEY_ITEM_DATE, items.getDate());

            values.put(KEY_ITEM_CODE, items.getItemCode());
            values.put(KEY_ITEM_NAME, items.getItemName());

            values.put(KEY_ITEM_COST, items.getCost());
            values.put(KEY_ITEM_CATEGORY, items.getCategory());

            values.put(KEY_ITEM_DESCRIPTION, items.getDescription());
            values.put(KEY_ITEM_TYPE, items.getType());

            values.put(KEY_ITEM_DEL, items.getModifiedType());
            values.put(KEY_ITEM_KOTTYPE, items.getKotype());

            values.put(KEY_ITEM_NORMALRATE, items.getNormal_rate());
            values.put(KEY_ITEM_ACRATE, items.getAc_rate());

            db.insert(TABLE_ITEM_MASTER, null, values);
            db.close();
        }
        catch (Exception e){
            String exec = e.getMessage();
        }
    }

    public void Add_Discountmaster(Discount discount){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put(KEY_DISCOUNT_AUTO_ID,      discount.getId());
         values.put(KEY_DISCOUNT_ID,           discount.getDiscountId());
         values.put(KEY_DISCOUNT_DATE,         discount.getType());
         values.put(KEY_DISCOUNT_DESCRIPTION,  discount.getDescription());
         values.put(KEY_DISCOUNT_AMOUNT,       discount.getAmount());
         values.put(KEY_DISCOUNT_MODIFIEDTYPE, discount.getModifiedtype());
    }


    // Add IP Details
    public void Add_IP_Details(Ip contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IP_AUTOID,               contact.getId());
        values.put(KEY_IP_NO,                   contact.getIpno());
        db.insert(TABLE_IP_DETAILS, null, values);
        db.close();
    }

    public void Add_Categorymaster(Category category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_ID,             category.getCategoryId());
        values.put(KEY_CATEGORY_NAME,           category.getCategory());
        values.put(KEY_CATEGORY_TYPE,           category.getType());
        values.put(KEY_CATEGORY_MODIFIEDTYPE,   category.getModifiedType());
        db.insert(TABLE_CATEGORY_MASTER, null, values);
        db.close();
    }

    public void Add_Addonmaster(AddonMaster addons){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ADDON_ID,                addons.getAddonid());
        values.put(KEY_ADDON_DATE,              addons.getDate());
        values.put(KEY_ADDON_ITEMNAME,          addons.getItemName());
        values.put(KEY_ADDON_PRIORITY,          addons.getPriority());
        values.put(KEY_ADDON_GROUPNAME,         addons.getGroupName());
        values.put(KEY_ADDON_NAME,              addons.getAddon());
        values.put(KEY_ADDON_AMOUNT,            addons.getAmount());
        values.put(KEY_ADDON_TYPE,              addons.getType());
        values.put(KEY_ADDON_ITEMTYPE,          addons.getItemType());
        values.put(KEY_ADDON_MODIFIEDTYPE,      addons.getModifiedType());
        values.put(KEY_ADDON_KOTTYPE,           addons.getKtype());
        db.insert(TABLE_ADDONS_MASTER, null, values);
        db.close();
    }

    // Adding Kot Details
    public void Add_Kot(KotCount count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KOT_COUNT_KOTNO,         count.getKotNo());
        values.put(KEY_KOT_COUNT_CREATED_ON,    count.getCreatedOn());
        values.put(KEY_KOT_COUNT_STATUS,        count.getStatus());
        // Inserting Row
        db.insert(TABLE_KOT_COUNT, null, values);
        db.close();
    }

    // Adding KOT ADDONS Details
    public void Add_Kot_addons(KotAddons addons){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KOT_ADDON_LOCAL_ID,      addons.getLocalid());
        values.put(KEY_KOT_ADDON_KOTNO,         addons.getKotno());
        values.put(KEY_KOT_ADDON_ITEM_CODE,     addons.getItemcode());
        values.put(KEY_KOT_ADDON_ADDON,         addons.getAddon());
        values.put(KEY_KOT_ADDON_RATE,          addons.getRate());
        values.put(KEY_KOT_ADDON_QTY,           addons.getQty());
        values.put(KEY_KOT_ADDON_AMOUNT,        addons.getAmount());
        values.put(KEY_KOT_ADDON_STATUS,        addons.getStatus());
        db.insert(TABLE_KOT_ADDON_ORDER,null,values);
        db.close();
    }

    // Adding KOT ORDER DETAILS
    public void Add_Kot_Orders(KotOrders orders) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KOT_ORDER_LOCAL_ID,      orders.getLocalId());
        values.put(KEY_KOT_ORDER_TABLE_NAME,    orders.getTableName());
        values.put(KEY_KOT_ORDER_KOT,           orders.getKot());
        values.put(KEY_KOT_ORDER_WAITERCODE,    orders.getWaiterCode());
        values.put(KEY_KOT_ORDER_NO_OF_PERSON,  orders.getNoOfPerson());
        values.put(KEY_KOT_ORDER_ITEM_CODES,    orders.getItemCode());
        values.put(KEY_KOT_ORDER_ITEM_NAME,     orders.getItemName());
        values.put(KEY_KOT_ORDER_ITEM_RATE,     orders.getItemRate());
        values.put(KEY_KOT_ORDER_ADDON_AMOUNT,  orders.getAddOnAmount());
        values.put(KEY_KOT_ORDER_QTY,           orders.getQty());
        values.put(KEY_KOT_ORDER_AMOUNT,        orders.getAmount());
        values.put(KEY_KOT_ORDER_STARTED_AT,    orders.getStartedAt());
        values.put(KEY_KOT_ORDER_FINISHED_AT,   orders.getFinishedAt());
        values.put(KEY_KOT_ORDER_INVOICE_NO,    orders.getInvoiceNo());
        values.put(KEY_KOT_ORDER_MODIFIED_DATE, orders.getModifiedDate());
        values.put(KEY_KOT_ORDER_MODIFIED_TYPE, orders.getModifiedType());
        values.put(KEY_KOT_ORDER_ROOM_TYPE,     orders.getRoomType());
        values.put(KEY_KOT_ORDER_SUGGESTION,    orders.getSuggestion());
        values.put(TABLE_KOT_ORDER_TYPE,orders.getOrdertype());
        db.insert(TABLE_KOT_ORDER, null, values);
        db.close();
    }

    // Add Sales Details
    public void Add_Sales_Details(Sales sales) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SALES_LOCAL_ID,          sales.getLocalId());
        values.put(KEY_SALES_KOTNO,             sales.getKotNo());
        values.put(KEY_SALES_INVOICE_DATE,      sales.getInvoiceDate());
        values.put(KEY_SALES_INVOICE_NO,        sales.getInvoiceNo());
        values.put(KEY_SALES_TABLE_NO,          sales.getTableNo());
        values.put(KEY_SALES_NO_OF_PERSONS,     sales.getNoofPersons());
        values.put(KEY_SALES_BILL_AMOUNT,       sales.getBillAmount());
        values.put(KEY_SALES_GST_AMOUNT,        sales.getGstAmount());
        values.put(KEY_SALES_EXTRA_CHARGES,     sales.getExtraCharges());
        values.put(KEY_SALES_TOTAL_AMOUNT,      sales.getTotalAmount());
        values.put(KEY_SALES_DISCOUNT,          sales.getDiscountAmount());
        values.put(KEY_SALES_PAYABLE_AMOUNT,    sales.getPayableAmount());
        values.put(KEY_SALES_GIVEN_AMOUNT,      sales.getGivenAmount());
        values.put(KEY_SALES_WAITER,            sales.getWaiter());
        values.put(KEY_SALES_BALANCE,           sales.getBalanceAmount());
        values.put(KEY_SALES_MODIFIED_BY,       sales.getModifiedBy());
        values.put(KEY_SALES_MODIFIED_DATE,     sales.getModifiedDate());
        db.insert(TABLE_SALES_DETAILS, null, values);
        db.close();
    }

    // Getting All Tables
    public ArrayList<Tables> Get_Table() {
        try {
            table_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_TABLE_MASTER;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Tables tables = new Tables();
                    tables.setTid(Integer.parseInt(cursor.getString(0)));
                    tables.setTableid(Integer.parseInt(cursor.getString(1)));
                    tables.setTableCode(cursor.getString(2));
                    tables.setFloor(cursor.getString(3));
                    tables.setTableName(cursor.getString(4));
                    tables.setTablePosition(cursor.getString(5));
                    tables.setModifiedType(cursor.getString(7));
                    table_list.add(tables);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return table_list;
        } catch (Exception e) {
              Log.e("get_table", "" + e);
        }
        return table_list;
    }

    public ArrayList<Tables> Get_AC_Table() {
        try {
            table_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_TABLE_MASTER + " WHERE type="+"'"+"AC"+"'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Tables tables = new Tables();
                    tables.setTid(Integer.parseInt(cursor.getString(0)));
                    tables.setTableid(Integer.parseInt(cursor.getString(1)));
                    tables.setTableCode(cursor.getString(2));
                    tables.setFloor(cursor.getString(3));
                    tables.setTableName(cursor.getString(4));
                    tables.setTablePosition(cursor.getString(5));
                    tables.setModifiedType(cursor.getString(7));
                    table_list.add(tables);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return table_list;
        } catch (Exception e) {
            Log.e("get_table", "" + e);
        }
        return table_list;
    }


    public ArrayList<Tables> Get_NONAC_Table() {
        try {
            table_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_TABLE_MASTER + " WHERE type="+"'"+"NONAC"+"'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Tables tables = new Tables();
                    tables.setTid(Integer.parseInt(cursor.getString(0)));
                    tables.setTableid(Integer.parseInt(cursor.getString(1)));
                    tables.setTableCode(cursor.getString(2));
                    tables.setFloor(cursor.getString(3));
                    tables.setTableName(cursor.getString(4));
                    tables.setTablePosition(cursor.getString(5));
                    tables.setModifiedType(cursor.getString(7));
                    table_list.add(tables);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return table_list;
        } catch (Exception e) {
            Log.e("get_table", "" + e);
        }
        return table_list;
    }

    // Getting All Category
    public ArrayList<Category> Get_All_Category() {
        try {
            category_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY_MASTER + " ORDER BY id ASC";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category();
                    category.setCid(Integer.parseInt(cursor.getString(0)));
                    category.setCategoryId(Integer.parseInt(cursor.getString(1)));
                    category.setCategory(cursor.getString(2));
                    category.setType(cursor.getString(3));
                    category.setModifiedType(cursor.getString(4));
                    category_list.add(category);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return category_list;
        } catch (Exception e) {
            Log.e("category_list", "" + e);
        }
        return category_list;
    }

    public ArrayList<AddonMaster> Get_All_Addons() {
        try {
            adon_master_list  = new ArrayList<AddonMaster>();
            addon_master_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_ADDONS_MASTER;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    AddonMaster addon = new AddonMaster();
                    addon.setId(Integer.parseInt(cursor.getString(0)));
                    addon.setAddonid(Integer.parseInt(cursor.getString(1)));
                    addon.setDate(cursor.getString(2));
                    addon.setAddon(cursor.getString(3));
                    addon.setPriority(cursor.getString(4));
                    addon.setGroupName(cursor.getString(5));
                    addon.setAddon(cursor.getString(6));
                    addon.setAmount(cursor.getString(7));
                    addon.setType(cursor.getString(8));
                    addon.setItemType(cursor.getString(9));
                    addon.setModifiedType(cursor.getString(10));
                    addon_master_list.add(addon);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return addon_master_list;
        } catch (Exception e) {
            Log.e("addon_list", "" + e);
        }
        return addon_master_list;
    }

    // Get Selected Category
    public ArrayList<Category> Get_Selected_Category(String selectedcategory) {
        try {
            category_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY_MASTER + " WHERE category LIKE '%" + selectedcategory + "%'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category();
                    category.setCid(Integer.parseInt(cursor.getString(0)));
                    category.setCategoryId(Integer.parseInt(cursor.getString(1)));
                    category.setCategory(cursor.getString(2));
                    category_list.add(category);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return category_list;
        } catch (Exception e) {
            Log.e("get_selected_category", "" + e);
        }
        return category_list;
    }

    // Get Items by Specific Category
    public ArrayList<Items> GetItems_By_Category(String category) {
        try {
            item_list.clear();
           // String selectQuery = "SELECT  * FROM " + TABLE_ITEM_MASTER + " WHERE category= '" + category + "'";
            String selectQuery = "SELECT  * FROM " + TABLE_ITEM_MASTER + " WHERE category= '" + category + "' ORDER BY id ASC";
           // String selectQuery = "SELECT  * FROM " + TABLE_ITEM_MASTER ;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Items items = new Items();
                    items.setId(Integer.parseInt(cursor.getString(0)));
                    items.setItemId(Integer.parseInt(cursor.getString(1)));
                    items.setDate(cursor.getString(2));
                    items.setItemCode(cursor.getString(3));
                    items.setItemName(cursor.getString(4));
                    items.setCost(cursor.getString(5));
                    items.setCategory(cursor.getString(6));
                    items.setDescription(cursor.getString(7));
                    items.setType(cursor.getString(8));
                    items.setModifiedType(cursor.getString(9));
                    items.setNormal_rate(cursor.getString(11));
                    items.setAc_rate(cursor.getString(12));
                    item_list.add(items);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return item_list;
        } catch (Exception e) {
            Log.e("get_items_by_category", "" + e);
        }
        return item_list;
    }

    // Get Items by Specific Category
    public ArrayList<Items> GetItems_By_Category_byname(String category,String item) {
        try {
            item_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_ITEM_MASTER + " WHERE category= '" + category + "' and itemname LIKE '%" + item + "%'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Items items = new Items();
                    items.setId(Integer.parseInt(cursor.getString(0)));
                    items.setItemId(Integer.parseInt(cursor.getString(1)));
                    items.setDate(cursor.getString(2));
                    items.setItemCode(cursor.getString(3));
                    items.setItemName(cursor.getString(4));
                    items.setCost(cursor.getString(5));
                    items.setCategory(cursor.getString(6));
                    items.setDescription(cursor.getString(7));
                    items.setType(cursor.getString(8));
                    items.setModifiedType(cursor.getString(9));
                    item_list.add(items);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return item_list;
        } catch (Exception e) {
            Log.e("get_items_by_category", "" + e);
        }
        return item_list;
    }

    // Get KOT Items
    public ArrayList<KotOrdersView> Get_KOT_Orders_Items(String kot_no) {
        try {
            String item_type="Item";
            kot_view_list.clear();
          //  String selectQuery = "SELECT * FROM "+ TABLE_KOT_ORDER +" WHERE localid="+localid+" and roomtype='Item'";
            String selectQuery = "SELECT * FROM "+ TABLE_KOT_ORDER +" WHERE invoiceno="+"'"+kot_no+"'";
           // String selectQuery = "SELECT * FROM "+ TABLE_KOT_ORDER +" WHERE localid="+localid+" ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    KotOrdersView items = new KotOrdersView();
                    items.setId(Integer.parseInt(cursor.getString(0)));
                    items.setItemCode(cursor.getString(6));
                    items.setItemName(cursor.getString(7));
                    items.setItemQty(cursor.getString(10));
                    items.setAmount(cursor.getString(11));
                    items.setSuggestion(cursor.getString(18));
                    kot_view_list.add(items);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kot_view_list;
        } catch (Exception e) {
            Log.e("get_kot_items", "" + e);
        }
        return kot_view_list;
    }


    public ArrayList<KotOrdersView> Get_KOT_Orders_Unfinished(String kot_no) {
        try {
            String item_type="Item";
            kot_view_list.clear();
            String selectQuery = "SELECT * FROM "+ TABLE_KOT_ORDER +" WHERE invoiceno="+"'"+kot_no+"'"+" and finishedat='0'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    KotOrdersView items = new KotOrdersView();
                    items.setId(Integer.parseInt(cursor.getString(0)));
                    items.setItemCode(cursor.getString(6));
                    items.setItemName(cursor.getString(7));
                    items.setItemQty(cursor.getString(10));
                    items.setAmount(cursor.getString(11));
                    items.setSuggestion(cursor.getString(18));
                    kot_view_list.add(items);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kot_view_list;
        } catch (Exception e) {
            Log.e("get_kot_items", "" + e);
        }
        return kot_view_list;
    }

    // Get KOT Items
    public ArrayList<EditTablesDetails> Get_KOT_Tables(String kott_no) {
        try {

            String item_type="Item";
            String Query="SELECT * FROM kotorder WHERE invoiceno="+"'"+kott_no+"'";
            edit_table_list.clear();
           // String selectQuery = "SELECT * FROM "+ TABLE_KOT_ORDER +" WHERE localid="+localid+" and roomtype='Item'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(Query, null);
            if (cursor.moveToFirst()) {
                do {
                    EditTablesDetails details = new EditTablesDetails();
                    details.setLocalId(cursor.getString(1));
                    details.setTableName(cursor.getString(2));
                    details.setServerKot(cursor.getString(3));
                    details.setWaiterName(cursor.getString(4));
                    details.setNoofPersons(cursor.getString(5));
                    details.setOrderType(cursor.getString(19));
                    details.setDate(cursor.getString(15));
                    details.setRoomType(cursor.getString(17));
                    edit_table_list.add(details);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return edit_table_list;
        } catch (Exception e) {
            Log.e("get_kot_items", "" + e);
        }
        return edit_table_list;
    }

    // Get KOT Items
    public ArrayList<KotOrdersView> Get_KOT_Item_byName(String localid,String itemcode) {
        try {

            String item_type="Item";
            kot_view_list.clear();
            String selectQuery = "SELECT * FROM "+ TABLE_KOT_ORDER +" WHERE localid="+localid+" and itemcodes="+itemcode+" and roomtype='Item'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    KotOrdersView items = new KotOrdersView();
                    items.setId(Integer.parseInt(cursor.getString(0)));
                    items.setItemCode(cursor.getString(6));
                    items.setItemName(cursor.getString(7));
                    items.setItemRate(cursor.getString(8));
                    items.setItemQty(cursor.getString(10));
                    items.setAmount(cursor.getString(11));
                    kot_view_list.add(items);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kot_view_list;
        } catch (Exception e) {
            Log.e("get_kot_items", "" + e);
        }
        return kot_view_list;
    }


    // Get KOT Items
    public ArrayList<KotAddonList> Get_KOT_Addons_Items(String localid,String itemcode) {
        try {
            kot_addon_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_KOT_ADDON_ORDER + " WHERE localid="+localid+" and itemcode="+itemcode;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    KotAddonList items = new KotAddonList();
                    items.setAddonId(Integer.parseInt(cursor.getString(0)));
                    items.setAddonCode(cursor.getString(3));
                    items.setAddonName(cursor.getString(4));
                    items.setAddonQty(cursor.getString(6));
                    items.setAddonAmount(cursor.getString(7));
                    kot_addon_list.add(items);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kot_addon_list;
        } catch (Exception e) {
            Log.e("get_kot_addons", "" + e);
        }
        return kot_addon_list;
    }

    // Getting Some AddonsItems
    public ArrayList<AddonSectionCell> Get_Addons_by_Item(String item) {
        try {
            adon_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_ADDONS_MASTER + " WHERE itemname= '" + item + "' and priority='1'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                  //  AddonMaster addon = new AddonMaster();
                    AddonSectionCell addon = new AddonSectionCell();
                    addon.setId(Integer.parseInt(cursor.getString(0)));
                    addon.setAddonid(Integer.parseInt(cursor.getString(1)));
                    addon.setDate(cursor.getString(2));
                    addon.setAddon(cursor.getString(3));
                    addon.setPriority(cursor.getString(4));
                    addon.setGroupName(cursor.getString(5));
                    addon.setItemName(cursor.getString(6));
                    addon.setAmount(cursor.getString(7));
                    addon.setType(cursor.getString(8));
                    addon.setItemType(cursor.getString(9));
                    addon.setModifiedType(cursor.getString(10));
                    adon_list.add(addon);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return adon_list;
        } catch (Exception e) {
            Log.e("addon_list", "" + e);
        }
        return adon_list;
    }

    public ArrayList<AddonOptionalCell> Get_Addons_by_Item_Optional(String item) {
        try {
            // adon_master_list  =  new ArrayList<AddonMaster>();
            addon_optional_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_ADDONS_MASTER + " WHERE itemname= '" + item + "' and priority='2'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    //  AddonMaster addon = new AddonMaster();
                    AddonOptionalCell addon = new AddonOptionalCell();
                    addon.setId(Integer.parseInt(cursor.getString(0)));
                    addon.setAddonid(Integer.parseInt(cursor.getString(1)));
                    addon.setDate(cursor.getString(2));
                    addon.setAddon(cursor.getString(3));
                    addon.setPriority(cursor.getString(4));
                    addon.setGroupName(cursor.getString(5));
                    addon.setItemName(cursor.getString(6));
                    addon.setAmount(cursor.getString(7));
                    addon.setType(cursor.getString(8));
                    addon.setItemType(cursor.getString(9));
                    addon.setModifiedType(cursor.getString(10));
                    addon_optional_list.add(addon);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return addon_optional_list;
        } catch (Exception e) {
            Log.e("addon_optional_list", "" + e);
        }
        return addon_optional_list;
    }

    // Get Ref Id
    public ArrayList<KotCount>Get_refid(String no) {
        try {
            kotcount_list.clear();
            String selectQuery = "SELECT * FROM " + TABLE_KOT_COUNT + " WHERE kotno=" + no;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    KotCount contact = new KotCount();
                    contact.setLocalId(cursor.getString(0));
                    kotcount_list.add(contact);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kotcount_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("get_refid", "" + e);
        }
        return kotcount_list;
    }

    // Get Item Qty Based on LocalKotnumber and itemCode
    public ArrayList<KotOrdersView>Get_Item_Qty(String localKotno,String itemCode){
        try
        {
            kot_view_list.clear();
            String selectQuery = "SELECT qty FROM " + TABLE_KOT_ORDER + " WHERE invoiceno="+"'"+localKotno+"'"+" and itemcodes="+itemCode;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    KotOrdersView orders = new KotOrdersView();
                    orders.setItemQty(cursor.getString(0));
                    kot_view_list.add(orders);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kot_view_list;
        }catch (Exception e){
            Log.e("get_item_qty",""+e);
        }
        return kot_view_list;
    }


    // Get Addon Qty Based on LocalKotnumber and itemCode and adddonname
    public ArrayList<KotOrders>Get_Mandatory_Addon_Qty(String localKotid,String itemCode,String addonName){
        try
        {
            kot_order_addon_list.clear();
            String selectQuery = "SELECT qty FROM " + TABLE_KOT_ADDON_ORDER + " WHERE localid="+localKotid+" and itemcode="+itemCode+" and addon="+"+'"+addonName +"'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    KotOrders orders = new KotOrders();
                    orders.setQty(cursor.getString(0));
                    kot_order_addon_list.add(orders);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kot_order_addon_list;
        }catch (Exception e){
            Log.e("get_item_qty",""+e);
        }
        return kot_order_addon_list;
    }

    public int count_cart() {
        String countQuery = "SELECT  * FROM " + TABLE_KOT_COUNT + " WHERE status='0'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public int count_Addon() {
        String countQuery = "SELECT  * FROM " + TABLE_ADDONS_MASTER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public int Count_Optional_Addon(String item){
        String countQuery = "SELECT  * FROM " + TABLE_ADDONS_MASTER + " WHERE itemname= '" + item + "' and priority='2'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public void Delete_Waitermaster(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_WAITER_MASTER );
        db.close();
    }

    public void Delete_Tablemaster(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TABLE_MASTER );
        db.close();
    }

    public void Delete_Itemmaster(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ITEM_MASTER);
        db.close();
    }

    public void Delete_Categorymaster(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CATEGORY_MASTER);
        db.close();
    }

    public void Delete_Discountmaster(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DISCOUNT_MASTER);
        db.close();
    }

    public void Delete_Addonmaster(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ADDONS_MASTER);
        db.close();
    }

    public void Delete_IP() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_IP_DETAILS);
        db.close();
    }

    // Update KOT ORDER ITEMS
    // UPDATE Added QTY //
    public int Update_Cart_Qty(KotOrders orders) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KOT_ORDER_ITEM_CODES, orders.getItemCode());
      //  values.put(KEY_KOT_ORDER_ITEM_RATE, orders.getItemRate());
        values.put(KEY_KOT_ORDER_QTY,orders.getQty());
        values.put(KEY_KOT_ORDER_AMOUNT,orders.getAmount());
        if(!TextUtils.isEmpty(orders.getSuggestion())){
            values.put(KEY_KOT_ORDER_SUGGESTION,orders.getSuggestion());
        }
        return db.update(TABLE_KOT_ORDER, values, KEY_KOT_ORDER_ITEM_CODES + " = ? AND " + "invoiceno" + "=?", new String[]{String.valueOf(orders.getItemCode()), orders.getInvoiceNo()});
    }
    public int Update_Suggestion(KotOrders orders) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
      values.put(KEY_KOT_ORDER_SUGGESTION,orders.getSuggestion());
        return db.update(TABLE_KOT_ORDER, values, KEY_KOT_ORDER_ITEM_CODES + " = ? AND " + "invoiceno" + "=?", new String[]{String.valueOf(orders.getItemCode()), orders.getInvoiceNo()});
    }
    public int Update_Cart_Status(KotOrders orders) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_KOT_ORDER_FINISHED_AT,orders.getFinishedAt());
        // return db.update(TABLE_KOT_ORDER, values, KEY_KOT_ORDER_AUTO_ID + " = ? AND " + KEY_KOT_ORDER_LOCAL_ID + "=?",
        //  new String[]{String.valueOf(orders.getId()), orders.getLocalId()});

        return db.update(TABLE_KOT_ORDER, values, "invoiceno" + " = ? " ,
                new String[]{orders.getInvoiceNo()});
    }

    // UPDATE Added QTY //

    public int Update_Cart_Qtys(CartItemModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KOT_ORDER_QTY, contact.getQty());
        values.put(KEY_KOT_ORDER_ITEM_CODES, contact.getItem_code());
        values.put(KEY_KOT_ORDER_AMOUNT, contact.getTotal_price());
        return db.update(TABLE_KOT_ORDER, values, KEY_KOT_ORDER_ITEM_CODES + " = ? AND " + "invoiceno" + "=?",
                new String[]{String.valueOf(contact.getItem_code()), contact.getKotno()});
    }

    public Boolean Delete_Cart_Items(String itemId,String kot_no) {
        SQLiteDatabase db = this.getWritableDatabase();
       /* ContentValues values = new ContentValues();
        values.put(KEY_ITEMS_QTY, contact.getQty());
        values.put(KEY_ITEMS_LOCAL_ID, contact.getLoc_id());
        values.put(KEY_ITEMS_ID, contact.getItem_code());
        values.put(KEY_ITEMS_TOTALPRICE, contact.getTotal_price()); */

        String exeqString = "DELETE FROM " + TABLE_KOT_ORDER +" where itemcodes="+itemId+" AND  invoiceno="+"'"+kot_no+"'";
        db.execSQL(exeqString);
        db.close();
        return true;
    }

   public int Update_Addon_Qty(KotAddons orders) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KOT_ADDON_ITEM_CODE, orders.getItemcode());
        values.put(KEY_KOT_ADDON_ADDON,orders.getAddon());
        values.put(KEY_KOT_ADDON_QTY,orders.getQty());
        values.put(KEY_KOT_ORDER_AMOUNT,orders.getAmount());
        // return db.update(TABLE_KOT_ORDER, values, KEY_KOT_ORDER_AUTO_ID + " = ? AND " + KEY_KOT_ORDER_LOCAL_ID + "=?",
        //  new String[]{String.valueOf(orders.getId()), orders.getLocalId()});
        return db.update(TABLE_KOT_ADDON_ORDER, values, KEY_KOT_ADDON_ITEM_CODE + " = ? AND "+KEY_KOT_ADDON_ADDON + " = ? AND " + KEY_KOT_ADDON_LOCAL_ID + "=?",
                new String[]{String.valueOf(orders.getItemcode()),orders.getAddon(),orders.getLocalid()});
    }
    // Get All Kotnos
    public ArrayList<KotOrders>Get_KotNos() {
        try {
            kot_details_list.clear();
            String selectQuery = "SELECT * FROM " + TABLE_KOT_ORDER +"WHERE roomtype='Item'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {

                    KotOrders kot = new KotOrders();
                    kot.setLocalId(cursor.getString(0));
                    kot.setTableName(cursor.getString(1));
                    kot.setWaiterCode(cursor.getString(4));
                    kot.setNoOfPerson(cursor.getString(5));
                   // kot.setAmount(cursor.getString());
                    kot_details_list.add(kot);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kot_details_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("get_refid", "" + e);
        }
        return kot_details_list;
    }


    // Get All Kotnos
    public ArrayList<Sales>Get_Total_Amount(String localid) {
        try {
            sales_details_list.clear();
            String selectQuery = "SELECT * FROM " + TABLE_SALES_DETAILS;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Sales sales = new Sales();
                    sales.setLocalId(cursor.getString(0));
                    sales.setKotNo(cursor.getString(1));
                    sales.setInvoiceDate(cursor.getString(2));
                    sales.setInvoiceNo(cursor.getString(3));
                    sales.setTableNo(cursor.getString(4));
                    sales.setNoofPersons(cursor.getString(5));
                    sales.setBillAmount(cursor.getString(6));
                    sales.setGstAmount(cursor.getString(7));
                    sales.setExtraCharges(cursor.getString(8));
                    sales.setTotalAmount(cursor.getString(9));
                    sales.setDiscountAmount(cursor.getString(10));
                    sales.setPayableAmount(cursor.getString(11));
                    sales.setGivenAmount(cursor.getString(12));
                    sales.setBalanceAmount(cursor.getString(13));
                    sales.setWaiter(cursor.getString(14));
                    sales.setModifiedBy(cursor.getString(15));
                    sales.setModifiedDate(cursor.getString(16));
                    sales_details_list.add(sales);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return sales_details_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("get_sales", "" + e);
        }
        return sales_details_list;
    }

    // Get All Kotnos
    public ArrayList<Sales>Get_Invoice_Details() {
        try {
            sales_details_list.clear();
            String selectQuery = "SELECT * FROM " + TABLE_SALES_DETAILS;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Sales sales = new Sales();
                    sales.setLocalId(cursor.getString(1));
                    sales.setKotNo(cursor.getString(2));
                    sales.setInvoiceDate(cursor.getString(3));
                    sales.setInvoiceNo(cursor.getString(4));
                    sales.setTableNo(cursor.getString(5));
                    sales.setNoofPersons(cursor.getString(6));
                    sales.setBillAmount(cursor.getString(7));
                    sales.setGstAmount(cursor.getString(8));
                    sales.setExtraCharges(cursor.getString(9));
                    sales.setTotalAmount(cursor.getString(10));
                    sales.setDiscountAmount(cursor.getString(11));
                    sales.setPayableAmount(cursor.getString(12));
                    sales.setGivenAmount(cursor.getString(13));
                    sales.setBalanceAmount(cursor.getString(14));
                    sales.setWaiter(cursor.getString(15));
                    sales.setModifiedBy(cursor.getString(16));
                    sales.setModifiedDate(cursor.getString(17));
                    sales_details_list.add(sales);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return sales_details_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("get_sales", "" + e);
        }
        return sales_details_list;
    }

    // Adding server kot number
    public void Add_Server_Kot(KotNoListModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SERVER_LOCID, contact.getLocal_id());
        values.put(KEY_SERVER_KOTNO, contact.getKot_no());
        values.put(KEY_SERVER_KOT_STATUS, contact.getStatus());
        db.insert(TABLE_KOT_SERVER, null, values);
        db.close();
    }

    public Boolean Delete_Kot(String localId,String kotNo) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
       /* ContentValues values = new ContentValues();
        values.put(KEY_ITEMS_QTY, contact.getQty());
        values.put(KEY_ITEMS_LOCAL_ID, contact.getLoc_id());
        values.put(KEY_ITEMS_ID, contact.getItem_code());
        values.put(KEY_ITEMS_TOTALPRICE, contact.getTotal_price()); */

            String exeqString = "DELETE FROM " + TABLE_KOT_SERVER + " where locid="+"'" + localId +"'"+" AND kotno=" +"'"+ kotNo+"'";
            // String sampleQuery = "SELECT * FROM kot";
            db.execSQL(exeqString);
            db.close();
        }
        catch (Exception e)
        {
            String err = e.getMessage();

        }
        return true;
    }

    // Getting Kotlist //
    public ArrayList<KotNoListModel> Get_KOT_List() {
        try {
            kot_no_list.clear();
            String selectQuery = "SELECT  * FROM " + TABLE_KOT_SERVER + " WHERE Status='0'ORDER BY kotno DESC";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    KotNoListModel contact = new KotNoListModel();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setKot_no(cursor.getString(1));
                    contact.setLocal_id(cursor.getString(2));
                    String stat = cursor.getString(3);
                    kot_no_list.add(contact);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kot_no_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("kot_list", "" + e);
        }
        return kot_no_list;
    }

    public ArrayList<CartItemModel> Get_Bill_Details(String kott_no) {
        try {
            cart_item_list.clear();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_KOT_ORDER + " WHERE invoiceno=" + "'"+kott_no+"'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    CartItemModel contact = new CartItemModel();
                    contact.setId(Integer.parseInt(cursor.getString(0)));
                    contact.setItem_code(cursor.getString(6));
                    contact.setQty(cursor.getString(10));
                    String price = cursor.getString(8);
                    contact.setPrice(price);
                    contact.setTotal_price(cursor.getString(11));
                    contact.setLoc_id(cursor.getString(1));
                    contact.setKotno(cursor.getString(3));
                    contact.setItem_name(cursor.getString(7));
                    contact.setFinished_at(cursor.getString(13)); // 0- not send to kitchen // 1- sent to kitchen
                    contact.setRoomtype(cursor.getString(17));
                    cart_item_list.add(contact);
                } while (cursor.moveToNext());
            }
            // return contact list
            cursor.close();
            db.close();
            return cart_item_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("cart_items", "" + e); }
        return cart_item_list;
    }
    public ArrayList<KotOrders> Get_kot_details(String kot_no){
ArrayList<KotOrders> kot_details=new ArrayList<KotOrders>();
        String Query="SELECT * FROM kotorder WHERE invoiceno="+"'"+kot_no+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            do {
                KotOrders kot_det=new KotOrders();
                kot_det.setModifiedDate(cursor.getString(15));
                kot_det.setRoomType(cursor.getString(17));
                kot_det.setOrdertype(cursor.getString(19));
                kot_det.setWaiterCode(cursor.getString(4));
               kot_det.setTableName(cursor.getString(2));
               kot_det.setLocalId(cursor.getString(1));
                kot_details.add(kot_det);
           /* KotNoListModel contact = new KotNoListModel();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setKot_no(cursor.getString(1));
            contact.setLocal_id(cursor.getString(2));
            String stat = cursor.getString(3);
            kot_no_list.add(contact);*/
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
return kot_details;
    }
}
