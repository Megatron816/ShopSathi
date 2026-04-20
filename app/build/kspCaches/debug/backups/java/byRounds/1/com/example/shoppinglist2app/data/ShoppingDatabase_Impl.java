package com.example.shoppinglist2app.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ShoppingDatabase_Impl extends ShoppingDatabase {
  private volatile ShoppingDao _shoppingDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `shopping_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `listId` INTEGER NOT NULL, `name` TEXT NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT NOT NULL, `category` TEXT NOT NULL, `price` REAL NOT NULL, `isChecked` INTEGER NOT NULL, `priority` TEXT NOT NULL, `eventDate` TEXT NOT NULL, `eventStatus` TEXT NOT NULL, `addedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `shopping_lists` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `isArchived` INTEGER NOT NULL, `totalBudget` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `colorHex` TEXT NOT NULL, `emoji` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `purchase_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `itemName` TEXT NOT NULL, `category` TEXT NOT NULL, `price` REAL NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT NOT NULL, `purchasedAt` INTEGER NOT NULL, `listName` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `list_templates` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `templateName` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `template_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `templateId` INTEGER NOT NULL, `itemName` TEXT NOT NULL, `quantity` REAL NOT NULL, `unit` TEXT NOT NULL, `category` TEXT NOT NULL, `price` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1d32c558f221f159b152949b45c18cf2')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `shopping_items`");
        db.execSQL("DROP TABLE IF EXISTS `shopping_lists`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `purchase_history`");
        db.execSQL("DROP TABLE IF EXISTS `list_templates`");
        db.execSQL("DROP TABLE IF EXISTS `template_items`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsShoppingItems = new HashMap<String, TableInfo.Column>(12);
        _columnsShoppingItems.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("listId", new TableInfo.Column("listId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("isChecked", new TableInfo.Column("isChecked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("priority", new TableInfo.Column("priority", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("eventDate", new TableInfo.Column("eventDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("eventStatus", new TableInfo.Column("eventStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingItems.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysShoppingItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesShoppingItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoShoppingItems = new TableInfo("shopping_items", _columnsShoppingItems, _foreignKeysShoppingItems, _indicesShoppingItems);
        final TableInfo _existingShoppingItems = TableInfo.read(db, "shopping_items");
        if (!_infoShoppingItems.equals(_existingShoppingItems)) {
          return new RoomOpenHelper.ValidationResult(false, "shopping_items(com.example.shoppinglist2app.data.ShoppingItem).\n"
                  + " Expected:\n" + _infoShoppingItems + "\n"
                  + " Found:\n" + _existingShoppingItems);
        }
        final HashMap<String, TableInfo.Column> _columnsShoppingLists = new HashMap<String, TableInfo.Column>(5);
        _columnsShoppingLists.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingLists.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingLists.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingLists.put("isArchived", new TableInfo.Column("isArchived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShoppingLists.put("totalBudget", new TableInfo.Column("totalBudget", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysShoppingLists = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesShoppingLists = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoShoppingLists = new TableInfo("shopping_lists", _columnsShoppingLists, _foreignKeysShoppingLists, _indicesShoppingLists);
        final TableInfo _existingShoppingLists = TableInfo.read(db, "shopping_lists");
        if (!_infoShoppingLists.equals(_existingShoppingLists)) {
          return new RoomOpenHelper.ValidationResult(false, "shopping_lists(com.example.shoppinglist2app.data.ShoppingList).\n"
                  + " Expected:\n" + _infoShoppingLists + "\n"
                  + " Found:\n" + _existingShoppingLists);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(4);
        _columnsCategories.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("colorHex", new TableInfo.Column("colorHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("emoji", new TableInfo.Column("emoji", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(com.example.shoppinglist2app.data.Category).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsPurchaseHistory = new HashMap<String, TableInfo.Column>(8);
        _columnsPurchaseHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPurchaseHistory.put("itemName", new TableInfo.Column("itemName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPurchaseHistory.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPurchaseHistory.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPurchaseHistory.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPurchaseHistory.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPurchaseHistory.put("purchasedAt", new TableInfo.Column("purchasedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPurchaseHistory.put("listName", new TableInfo.Column("listName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPurchaseHistory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPurchaseHistory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPurchaseHistory = new TableInfo("purchase_history", _columnsPurchaseHistory, _foreignKeysPurchaseHistory, _indicesPurchaseHistory);
        final TableInfo _existingPurchaseHistory = TableInfo.read(db, "purchase_history");
        if (!_infoPurchaseHistory.equals(_existingPurchaseHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "purchase_history(com.example.shoppinglist2app.data.PurchaseHistory).\n"
                  + " Expected:\n" + _infoPurchaseHistory + "\n"
                  + " Found:\n" + _existingPurchaseHistory);
        }
        final HashMap<String, TableInfo.Column> _columnsListTemplates = new HashMap<String, TableInfo.Column>(3);
        _columnsListTemplates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListTemplates.put("templateName", new TableInfo.Column("templateName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsListTemplates.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysListTemplates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesListTemplates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoListTemplates = new TableInfo("list_templates", _columnsListTemplates, _foreignKeysListTemplates, _indicesListTemplates);
        final TableInfo _existingListTemplates = TableInfo.read(db, "list_templates");
        if (!_infoListTemplates.equals(_existingListTemplates)) {
          return new RoomOpenHelper.ValidationResult(false, "list_templates(com.example.shoppinglist2app.data.ListTemplate).\n"
                  + " Expected:\n" + _infoListTemplates + "\n"
                  + " Found:\n" + _existingListTemplates);
        }
        final HashMap<String, TableInfo.Column> _columnsTemplateItems = new HashMap<String, TableInfo.Column>(7);
        _columnsTemplateItems.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTemplateItems.put("templateId", new TableInfo.Column("templateId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTemplateItems.put("itemName", new TableInfo.Column("itemName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTemplateItems.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTemplateItems.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTemplateItems.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTemplateItems.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTemplateItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTemplateItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTemplateItems = new TableInfo("template_items", _columnsTemplateItems, _foreignKeysTemplateItems, _indicesTemplateItems);
        final TableInfo _existingTemplateItems = TableInfo.read(db, "template_items");
        if (!_infoTemplateItems.equals(_existingTemplateItems)) {
          return new RoomOpenHelper.ValidationResult(false, "template_items(com.example.shoppinglist2app.data.TemplateItem).\n"
                  + " Expected:\n" + _infoTemplateItems + "\n"
                  + " Found:\n" + _existingTemplateItems);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1d32c558f221f159b152949b45c18cf2", "50b6efcb0008aaee149cf653c4cbc8da");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "shopping_items","shopping_lists","categories","purchase_history","list_templates","template_items");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `shopping_items`");
      _db.execSQL("DELETE FROM `shopping_lists`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `purchase_history`");
      _db.execSQL("DELETE FROM `list_templates`");
      _db.execSQL("DELETE FROM `template_items`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ShoppingDao.class, ShoppingDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ShoppingDao shoppingDao() {
    if (_shoppingDao != null) {
      return _shoppingDao;
    } else {
      synchronized(this) {
        if(_shoppingDao == null) {
          _shoppingDao = new ShoppingDao_Impl(this);
        }
        return _shoppingDao;
      }
    }
  }
}
