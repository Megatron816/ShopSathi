package com.example.shoppinglist2app.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ShoppingDao_Impl implements ShoppingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ShoppingList> __insertionAdapterOfShoppingList;

  private final EntityInsertionAdapter<Category> __insertionAdapterOfCategory;

  private final EntityInsertionAdapter<PurchaseHistory> __insertionAdapterOfPurchaseHistory;

  private final EntityInsertionAdapter<ListTemplate> __insertionAdapterOfListTemplate;

  private final EntityInsertionAdapter<TemplateItem> __insertionAdapterOfTemplateItem;

  private final EntityDeletionOrUpdateAdapter<ShoppingItem> __deletionAdapterOfShoppingItem;

  private final EntityDeletionOrUpdateAdapter<Category> __deletionAdapterOfCategory;

  private final EntityDeletionOrUpdateAdapter<ShoppingList> __updateAdapterOfShoppingList;

  private final SharedSQLiteStatement __preparedStmtOfArchiveList;

  private final SharedSQLiteStatement __preparedStmtOfRestoreList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllItemsForList;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllItemsForDate;

  private final SharedSQLiteStatement __preparedStmtOfSetChecked;

  private final SharedSQLiteStatement __preparedStmtOfUncheckAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTemplate;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTemplateItems;

  private final EntityUpsertionAdapter<ShoppingItem> __upsertionAdapterOfShoppingItem;

  public ShoppingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfShoppingList = new EntityInsertionAdapter<ShoppingList>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `shopping_lists` (`id`,`name`,`createdAt`,`isArchived`,`totalBudget`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShoppingList entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getCreatedAt());
        final int _tmp = entity.isArchived() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindDouble(5, entity.getTotalBudget());
      }
    };
    this.__insertionAdapterOfCategory = new EntityInsertionAdapter<Category>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `categories` (`id`,`name`,`colorHex`,`emoji`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Category entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getColorHex());
        statement.bindString(4, entity.getEmoji());
      }
    };
    this.__insertionAdapterOfPurchaseHistory = new EntityInsertionAdapter<PurchaseHistory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `purchase_history` (`id`,`itemName`,`category`,`price`,`quantity`,`unit`,`purchasedAt`,`listName`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PurchaseHistory entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getItemName());
        statement.bindString(3, entity.getCategory());
        statement.bindDouble(4, entity.getPrice());
        statement.bindDouble(5, entity.getQuantity());
        statement.bindString(6, entity.getUnit());
        statement.bindLong(7, entity.getPurchasedAt());
        statement.bindString(8, entity.getListName());
      }
    };
    this.__insertionAdapterOfListTemplate = new EntityInsertionAdapter<ListTemplate>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `list_templates` (`id`,`templateName`,`createdAt`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ListTemplate entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTemplateName());
        statement.bindLong(3, entity.getCreatedAt());
      }
    };
    this.__insertionAdapterOfTemplateItem = new EntityInsertionAdapter<TemplateItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `template_items` (`id`,`templateId`,`itemName`,`quantity`,`unit`,`category`,`price`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TemplateItem entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTemplateId());
        statement.bindString(3, entity.getItemName());
        statement.bindDouble(4, entity.getQuantity());
        statement.bindString(5, entity.getUnit());
        statement.bindString(6, entity.getCategory());
        statement.bindDouble(7, entity.getPrice());
      }
    };
    this.__deletionAdapterOfShoppingItem = new EntityDeletionOrUpdateAdapter<ShoppingItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `shopping_items` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShoppingItem entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfCategory = new EntityDeletionOrUpdateAdapter<Category>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `categories` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Category entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfShoppingList = new EntityDeletionOrUpdateAdapter<ShoppingList>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `shopping_lists` SET `id` = ?,`name` = ?,`createdAt` = ?,`isArchived` = ?,`totalBudget` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShoppingList entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getCreatedAt());
        final int _tmp = entity.isArchived() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindDouble(5, entity.getTotalBudget());
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfArchiveList = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE shopping_lists SET isArchived = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfRestoreList = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE shopping_lists SET isArchived = 0 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteList = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM shopping_lists WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllItemsForList = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM shopping_items WHERE listId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllItemsForDate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM shopping_items WHERE eventDate = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetChecked = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE shopping_items SET isChecked = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUncheckAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE shopping_items SET isChecked = 0 WHERE listId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteTemplate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM list_templates WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteTemplateItems = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM template_items WHERE templateId = ?";
        return _query;
      }
    };
    this.__upsertionAdapterOfShoppingItem = new EntityUpsertionAdapter<ShoppingItem>(new EntityInsertionAdapter<ShoppingItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT INTO `shopping_items` (`id`,`listId`,`name`,`quantity`,`unit`,`category`,`price`,`isChecked`,`priority`,`eventDate`,`eventStatus`,`addedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShoppingItem entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getListId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getQuantity());
        statement.bindString(5, entity.getUnit());
        statement.bindString(6, entity.getCategory());
        statement.bindDouble(7, entity.getPrice());
        final int _tmp = entity.isChecked() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindString(9, entity.getPriority());
        statement.bindString(10, entity.getEventDate());
        statement.bindString(11, entity.getEventStatus());
        statement.bindLong(12, entity.getAddedAt());
      }
    }, new EntityDeletionOrUpdateAdapter<ShoppingItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE `shopping_items` SET `id` = ?,`listId` = ?,`name` = ?,`quantity` = ?,`unit` = ?,`category` = ?,`price` = ?,`isChecked` = ?,`priority` = ?,`eventDate` = ?,`eventStatus` = ?,`addedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShoppingItem entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getListId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getQuantity());
        statement.bindString(5, entity.getUnit());
        statement.bindString(6, entity.getCategory());
        statement.bindDouble(7, entity.getPrice());
        final int _tmp = entity.isChecked() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindString(9, entity.getPriority());
        statement.bindString(10, entity.getEventDate());
        statement.bindString(11, entity.getEventStatus());
        statement.bindLong(12, entity.getAddedAt());
        statement.bindLong(13, entity.getId());
      }
    });
  }

  @Override
  public Object insertList(final ShoppingList list, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfShoppingList.insertAndReturnId(list);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCategory(final Category category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCategory.insert(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCategories(final List<Category> categories,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCategory.insert(categories);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertHistory(final PurchaseHistory history,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPurchaseHistory.insert(history);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertHistoryBatch(final List<PurchaseHistory> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPurchaseHistory.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTemplate(final ListTemplate template,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfListTemplate.insertAndReturnId(template);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTemplateItems(final List<TemplateItem> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTemplateItem.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteItem(final ShoppingItem item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfShoppingItem.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCategory(final Category category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCategory.handle(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateList(final ShoppingList list, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfShoppingList.handle(list);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object archiveList(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfArchiveList.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfArchiveList.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object restoreList(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfRestoreList.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfRestoreList.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteList(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteList.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteList.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllItemsForList(final int listId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllItemsForList.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, listId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllItemsForList.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllItemsForDate(final String date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllItemsForDate.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, date);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllItemsForDate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setChecked(final int id, final boolean checked,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetChecked.acquire();
        int _argIndex = 1;
        final int _tmp = checked ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetChecked.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object uncheckAll(final int listId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUncheckAll.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, listId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUncheckAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTemplate(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTemplate.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteTemplate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTemplateItems(final int templateId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTemplateItems.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, templateId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteTemplateItems.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertItem(final ShoppingItem item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __upsertionAdapterOfShoppingItem.upsert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ShoppingList>> getActiveLists() {
    final String _sql = "SELECT * FROM shopping_lists WHERE isArchived = 0 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shopping_lists"}, new Callable<List<ShoppingList>>() {
      @Override
      @NonNull
      public List<ShoppingList> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfTotalBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBudget");
          final List<ShoppingList> _result = new ArrayList<ShoppingList>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ShoppingList _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            final double _tmpTotalBudget;
            _tmpTotalBudget = _cursor.getDouble(_cursorIndexOfTotalBudget);
            _item = new ShoppingList(_tmpId,_tmpName,_tmpCreatedAt,_tmpIsArchived,_tmpTotalBudget);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ShoppingList>> getArchivedLists() {
    final String _sql = "SELECT * FROM shopping_lists WHERE isArchived = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shopping_lists"}, new Callable<List<ShoppingList>>() {
      @Override
      @NonNull
      public List<ShoppingList> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfTotalBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBudget");
          final List<ShoppingList> _result = new ArrayList<ShoppingList>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ShoppingList _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            final double _tmpTotalBudget;
            _tmpTotalBudget = _cursor.getDouble(_cursorIndexOfTotalBudget);
            _item = new ShoppingList(_tmpId,_tmpName,_tmpCreatedAt,_tmpIsArchived,_tmpTotalBudget);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ShoppingItem>> getItemsForList(final int listId) {
    final String _sql = "\n"
            + "        SELECT * FROM shopping_items WHERE listId = ?\n"
            + "        ORDER BY CASE priority WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 ELSE 3 END, category ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shopping_items"}, new Callable<List<ShoppingItem>>() {
      @Override
      @NonNull
      public List<ShoppingItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfIsChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "isChecked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfEventDate = CursorUtil.getColumnIndexOrThrow(_cursor, "eventDate");
          final int _cursorIndexOfEventStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "eventStatus");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final List<ShoppingItem> _result = new ArrayList<ShoppingItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ShoppingItem _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpListId;
            _tmpListId = _cursor.getInt(_cursorIndexOfListId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final boolean _tmpIsChecked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsChecked);
            _tmpIsChecked = _tmp != 0;
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpEventDate;
            _tmpEventDate = _cursor.getString(_cursorIndexOfEventDate);
            final String _tmpEventStatus;
            _tmpEventStatus = _cursor.getString(_cursorIndexOfEventStatus);
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            _item = new ShoppingItem(_tmpId,_tmpListId,_tmpName,_tmpQuantity,_tmpUnit,_tmpCategory,_tmpPrice,_tmpIsChecked,_tmpPriority,_tmpEventDate,_tmpEventStatus,_tmpAddedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ShoppingItem>> getItemsByDate(final String date) {
    final String _sql = "SELECT * FROM shopping_items WHERE eventDate = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shopping_items"}, new Callable<List<ShoppingItem>>() {
      @Override
      @NonNull
      public List<ShoppingItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfIsChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "isChecked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfEventDate = CursorUtil.getColumnIndexOrThrow(_cursor, "eventDate");
          final int _cursorIndexOfEventStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "eventStatus");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final List<ShoppingItem> _result = new ArrayList<ShoppingItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ShoppingItem _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpListId;
            _tmpListId = _cursor.getInt(_cursorIndexOfListId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final boolean _tmpIsChecked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsChecked);
            _tmpIsChecked = _tmp != 0;
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpEventDate;
            _tmpEventDate = _cursor.getString(_cursorIndexOfEventDate);
            final String _tmpEventStatus;
            _tmpEventStatus = _cursor.getString(_cursorIndexOfEventStatus);
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            _item = new ShoppingItem(_tmpId,_tmpListId,_tmpName,_tmpQuantity,_tmpUnit,_tmpCategory,_tmpPrice,_tmpIsChecked,_tmpPriority,_tmpEventDate,_tmpEventStatus,_tmpAddedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<String>> getAllEventDates() {
    final String _sql = "SELECT DISTINCT eventDate FROM shopping_items ORDER BY eventDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shopping_items"}, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ShoppingItem>> getTodayItems() {
    final String _sql = "SELECT * FROM shopping_items WHERE eventStatus = 'today' ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shopping_items"}, new Callable<List<ShoppingItem>>() {
      @Override
      @NonNull
      public List<ShoppingItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfIsChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "isChecked");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfEventDate = CursorUtil.getColumnIndexOrThrow(_cursor, "eventDate");
          final int _cursorIndexOfEventStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "eventStatus");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final List<ShoppingItem> _result = new ArrayList<ShoppingItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ShoppingItem _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpListId;
            _tmpListId = _cursor.getInt(_cursorIndexOfListId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final boolean _tmpIsChecked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsChecked);
            _tmpIsChecked = _tmp != 0;
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            final String _tmpEventDate;
            _tmpEventDate = _cursor.getString(_cursorIndexOfEventDate);
            final String _tmpEventStatus;
            _tmpEventStatus = _cursor.getString(_cursorIndexOfEventStatus);
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            _item = new ShoppingItem(_tmpId,_tmpListId,_tmpName,_tmpQuantity,_tmpUnit,_tmpCategory,_tmpPrice,_tmpIsChecked,_tmpPriority,_tmpEventDate,_tmpEventStatus,_tmpAddedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Double> getTotalSpent(final int listId) {
    final String _sql = "SELECT COALESCE(SUM(price * quantity), 0.0) FROM shopping_items WHERE listId = ? AND isChecked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shopping_items"}, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Double> getTotalEstimate(final int listId) {
    final String _sql = "SELECT COALESCE(SUM(price * quantity), 0.0) FROM shopping_items WHERE listId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shopping_items"}, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Category>> getAllCategories() {
    final String _sql = "SELECT * FROM categories ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"categories"}, new Callable<List<Category>>() {
      @Override
      @NonNull
      public List<Category> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "emoji");
          final List<Category> _result = new ArrayList<Category>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Category _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final String _tmpEmoji;
            _tmpEmoji = _cursor.getString(_cursorIndexOfEmoji);
            _item = new Category(_tmpId,_tmpName,_tmpColorHex,_tmpEmoji);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCategoryNames(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT name FROM categories";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCategoryCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM categories";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Double> getMonthlySpend(final long monthStart, final long monthEnd) {
    final String _sql = "SELECT COALESCE(SUM(price * quantity), 0.0) FROM purchase_history WHERE purchasedAt >= ? AND purchasedAt <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, monthStart);
    _argIndex = 2;
    _statement.bindLong(_argIndex, monthEnd);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"purchase_history"}, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<CategorySpend>> getCategoryBreakdown(final long monthStart,
      final long monthEnd) {
    final String _sql = "\n"
            + "        SELECT category, SUM(price * quantity) as totalSpent\n"
            + "        FROM purchase_history\n"
            + "        WHERE purchasedAt >= ? AND purchasedAt <= ?\n"
            + "        GROUP BY category ORDER BY totalSpent DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, monthStart);
    _argIndex = 2;
    _statement.bindLong(_argIndex, monthEnd);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"purchase_history"}, new Callable<List<CategorySpend>>() {
      @Override
      @NonNull
      public List<CategorySpend> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategory = 0;
          final int _cursorIndexOfTotalSpent = 1;
          final List<CategorySpend> _result = new ArrayList<CategorySpend>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategorySpend _item;
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpTotalSpent;
            _tmpTotalSpent = _cursor.getDouble(_cursorIndexOfTotalSpent);
            _item = new CategorySpend(_tmpCategory,_tmpTotalSpent);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ItemFrequency>> getSuggestions(final String query) {
    final String _sql = "\n"
            + "        SELECT itemName, COUNT(*) as frequency\n"
            + "        FROM purchase_history\n"
            + "        WHERE itemName LIKE ? || '%'\n"
            + "        GROUP BY itemName\n"
            + "        ORDER BY frequency DESC\n"
            + "        LIMIT 10\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"purchase_history"}, new Callable<List<ItemFrequency>>() {
      @Override
      @NonNull
      public List<ItemFrequency> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfItemName = 0;
          final int _cursorIndexOfFrequency = 1;
          final List<ItemFrequency> _result = new ArrayList<ItemFrequency>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemFrequency _item;
            final String _tmpItemName;
            _tmpItemName = _cursor.getString(_cursorIndexOfItemName);
            final int _tmpFrequency;
            _tmpFrequency = _cursor.getInt(_cursorIndexOfFrequency);
            _item = new ItemFrequency(_tmpItemName,_tmpFrequency);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ItemFrequency>> getTopItems() {
    final String _sql = "\n"
            + "        SELECT itemName, COUNT(*) as frequency\n"
            + "        FROM purchase_history\n"
            + "        GROUP BY itemName\n"
            + "        ORDER BY frequency DESC\n"
            + "        LIMIT 5\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"purchase_history"}, new Callable<List<ItemFrequency>>() {
      @Override
      @NonNull
      public List<ItemFrequency> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfItemName = 0;
          final int _cursorIndexOfFrequency = 1;
          final List<ItemFrequency> _result = new ArrayList<ItemFrequency>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemFrequency _item;
            final String _tmpItemName;
            _tmpItemName = _cursor.getString(_cursorIndexOfItemName);
            final int _tmpFrequency;
            _tmpFrequency = _cursor.getInt(_cursorIndexOfFrequency);
            _item = new ItemFrequency(_tmpItemName,_tmpFrequency);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ListTemplate>> getAllTemplates() {
    final String _sql = "SELECT * FROM list_templates ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"list_templates"}, new Callable<List<ListTemplate>>() {
      @Override
      @NonNull
      public List<ListTemplate> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTemplateName = CursorUtil.getColumnIndexOrThrow(_cursor, "templateName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ListTemplate> _result = new ArrayList<ListTemplate>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ListTemplate _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTemplateName;
            _tmpTemplateName = _cursor.getString(_cursorIndexOfTemplateName);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ListTemplate(_tmpId,_tmpTemplateName,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<TemplateItem>> getTemplateItems(final int templateId) {
    final String _sql = "SELECT * FROM template_items WHERE templateId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, templateId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"template_items"}, new Callable<List<TemplateItem>>() {
      @Override
      @NonNull
      public List<TemplateItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfItemName = CursorUtil.getColumnIndexOrThrow(_cursor, "itemName");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final List<TemplateItem> _result = new ArrayList<TemplateItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TemplateItem _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpTemplateId;
            _tmpTemplateId = _cursor.getInt(_cursorIndexOfTemplateId);
            final String _tmpItemName;
            _tmpItemName = _cursor.getString(_cursorIndexOfItemName);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpUnit;
            _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            _item = new TemplateItem(_tmpId,_tmpTemplateId,_tmpItemName,_tmpQuantity,_tmpUnit,_tmpCategory,_tmpPrice);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTemplateItemCount(final int templateId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM template_items WHERE templateId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, templateId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
