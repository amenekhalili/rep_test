package ir.fararayaneh.erp.utils.database_handler;

import androidx.annotation.NonNull;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import ir.fararayaneh.erp.data.models.tables.sync_tables.FuelListDetailTable;

public class MyMigrations implements RealmMigration {
    @Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema realmSchema = realm.getSchema();
//--------------------------------------------------------------------------------
        if (oldVersion == 1) {

            realmSchema.create("EvacuatePacketTable")
                    .addField("guid", String.class, FieldAttribute.PRIMARY_KEY)
                    .addField("jsonPacket", String.class)
                    .addField("syncState", String.class);

            oldVersion++;
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 2) {

            RealmObjectSchema formRefTable = realmSchema.get("FormRefTable");
            assert formRefTable != null;
            formRefTable.addField("formLocation",int.class);
            oldVersion++;
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 3) {

            RealmObjectSchema fuelListDetailTable = realmSchema.get("FuelListDetailTable");
            assert fuelListDetailTable != null;
            fuelListDetailTable.removeField("costPrice");
            fuelListDetailTable.removeField("unitPrice");
            oldVersion++;
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 4) {

            RealmObjectSchema fuelListDetailTable = realmSchema.get("FuelListDetailTable");
            assert fuelListDetailTable != null;
            fuelListDetailTable.renameField("goodName","deviceName");
            fuelListDetailTable.renameField("assetName","placeName");
            oldVersion++;
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 5) {

            RealmObjectSchema fuelListMasterTable = realmSchema.get("FuelListMasterTable");
            assert fuelListMasterTable != null;
            fuelListMasterTable.addField("departmentName",String.class);
            oldVersion++;
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 6) {

            RealmObjectSchema messageTable = realmSchema.get("MessageTable");
            assert messageTable != null;
            messageTable.addField("isOld",String.class);
            oldVersion++;
        }
//--------------------------------------------------------------------------------
      /*  if (oldVersion == 1) {

            //-------------------------------------------------------------
            RealmObjectSchema agroFieldTable = realmSchema.get("AgroFieldTable");
            assert agroFieldTable != null;
            agroFieldTable.addField("id_tmp", String.class);
            agroFieldTable.addField("J5OrgChartId_tmp", String.class);
            agroFieldTable.addField("surface_tmp", String.class);
            agroFieldTable.addField("rowCount_tmp", String.class);
            agroFieldTable.addField("segmentCount_tmp", String.class);

            agroFieldTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int J5OrgChartId = obj.getInt("J5OrgChartId");
                    int surface = obj.getInt("surface");
                    int rowCount = obj.getInt("rowCount");
                    int segmentCount = obj.getInt("segmentCount");
                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("J5OrgChartId_tmp", String.valueOf(J5OrgChartId));
                    obj.setString("surface_tmp", String.valueOf(surface));
                    obj.setString("rowCount_tmp", String.valueOf(rowCount));
                    obj.setString("segmentCount_tmp", String.valueOf(segmentCount));

                }
            });

            agroFieldTable.removeField("id");
            agroFieldTable.removeField("J5OrgChartId");
            agroFieldTable.removeField("surface");
            agroFieldTable.removeField("rowCount");
            agroFieldTable.removeField("segmentCount");
            agroFieldTable.renameField("id_tmp","id");
            agroFieldTable.renameField("J5OrgChartId_tmp","J5OrgChartId");
            agroFieldTable.renameField("surface_tmp","surface" );
            agroFieldTable.renameField("rowCount_tmp","rowCount");
            agroFieldTable.renameField("segmentCount_tmp","segmentCount");
            agroFieldTable.addPrimaryKey("id");
            //-------------------------------------------------------------
            RealmObjectSchema baseCodingTable = realmSchema.get("BaseCodingTable");

            assert baseCodingTable != null;
            baseCodingTable.addField("id_tmp", String.class);
            baseCodingTable.addField("B5FormRefId_tmp", String.class);
            baseCodingTable.addField("nationalityId_tmp", String.class);
            baseCodingTable.addField("code_tmp", String.class);

            baseCodingTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int B5FormRefId = obj.getInt("B5FormRefId");
                    int nationalityId = obj.getInt("nationalityId");
                    int code = obj.getInt("code");
                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("B5FormRefId_tmp", String.valueOf(B5FormRefId));
                    obj.setString("nationalityId_tmp", String.valueOf(nationalityId));
                    obj.setString("code_tmp", String.valueOf(code));
                }
            });

            baseCodingTable.removeField("id");
            baseCodingTable.removeField("B5FormRefId");
            baseCodingTable.removeField("nationalityId");
            baseCodingTable.removeField("code");
            baseCodingTable.renameField("id_tmp","id");
            baseCodingTable.renameField("B5FormRefId_tmp","B5FormRefId");
            baseCodingTable.renameField("nationalityId_tmp","nationalityId");
            baseCodingTable.renameField("code_tmp","code");
            baseCodingTable.addPrimaryKey("id");
            //-------------------------------------------------------------
            RealmObjectSchema formRefTable = realmSchema.get("FormRefTable");

            assert formRefTable != null;
            formRefTable.addField("formId_tmp", String.class);
            formRefTable.addField("formCode_tmp", String.class);

            formRefTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int formId = obj.getInt("formId");
                    int formCode = obj.getInt("formCode");
                    obj.setString("formId_tmp", String.valueOf(formId));
                    obj.setString("formCode_tmp", String.valueOf(formCode));

                }
            });

            formRefTable.removeField("formId");
            formRefTable.removeField("formCode");

            formRefTable.renameField("formId_tmp","formId");
            formRefTable.renameField("formCode_tmp","formCode");

            formRefTable.addPrimaryKey("formId");
            //-------------------------------------------------------------
            RealmObjectSchema goodsTable = realmSchema.get("GoodsTable");

            assert goodsTable != null;
            goodsTable.addField("id_tmp", String.class);
            goodsTable.addField("C5UnitId_tmp", String.class);
            goodsTable.addField("C5BrandId_tmp", String.class);
            goodsTable.addField("B5HCStatusId_tmp", String.class);
            goodsTable.addField("width_tmp", String.class);
            goodsTable.addField("height_tmp", String.class);
            goodsTable.addField("length_tmp", String.class);
            goodsTable.addField("weight_tmp", String.class);

            goodsTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int C5UnitId = obj.getInt("C5UnitId");
                    int C5BrandId = obj.getInt("C5BrandId");
                    int B5HCStatusId = obj.getInt("B5HCStatusId");
                    int width = obj.getInt("width");
                    int height = obj.getInt("height");
                    int length = obj.getInt("length");
                    int weight = obj.getInt("weight");

                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("C5UnitId_tmp", String.valueOf(C5UnitId));
                    obj.setString("C5BrandId_tmp", String.valueOf(C5BrandId));
                    obj.setString("B5HCStatusId_tmp", String.valueOf(B5HCStatusId));
                    obj.setString("width_tmp", String.valueOf(width));
                    obj.setString("height_tmp", String.valueOf(height));
                    obj.setString("length_tmp", String.valueOf(length));
                    obj.setString("weight_tmp", String.valueOf(weight));
                }
            });

            goodsTable.removeField("id");
            goodsTable.removeField("C5UnitId");
            goodsTable.removeField("C5BrandId");
            goodsTable.removeField("B5HCStatusId");
            goodsTable.removeField("width");
            goodsTable.removeField("height");
            goodsTable.removeField("length");
            goodsTable.removeField("weight");

            goodsTable.renameField("id_tmp","id");
            goodsTable.renameField("C5UnitId_tmp","C5UnitId");
            goodsTable.renameField("C5BrandId_tmp","C5BrandId");
            goodsTable.renameField("B5HCStatusId_tmp","B5HCStatusId");
            goodsTable.renameField("width_tmp","width");
            goodsTable.renameField("height_tmp","height");
            goodsTable.renameField("length_tmp","length");
            goodsTable.renameField("weight_tmp","weight");

            goodsTable.addPrimaryKey("id");

            //-------------------------------------------------------------
            RealmObjectSchema goodSUOMTable = realmSchema.get("GoodSUOMTable");

            assert goodSUOMTable != null;
            goodSUOMTable.addField("id_tmp", String.class);
            goodSUOMTable.addField("C5GoodsId_tmp", String.class);
            goodSUOMTable.addField("C5UnitId_tmp", String.class);
            goodSUOMTable.addField("weight_tmp", String.class);
            goodSUOMTable.addField("length_tmp", String.class);
            goodSUOMTable.addField("width_tmp", String.class);
            goodSUOMTable.addField("height_tmp", String.class);

            goodSUOMTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int C5GoodsId = obj.getInt("C5GoodsId");
                    int C5UnitId = obj.getInt("C5UnitId");
                    int weight = obj.getInt("weight");
                    int length = obj.getInt("length");
                    int width = obj.getInt("width");
                    int height = obj.getInt("height");

                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("C5GoodsId_tmp", String.valueOf(C5GoodsId));
                    obj.setString("C5UnitId_tmp", String.valueOf(C5UnitId));
                    obj.setString("weight_tmp", String.valueOf(weight));
                    obj.setString("length_tmp", String.valueOf(length));
                    obj.setString("width_tmp", String.valueOf(width));
                    obj.setString("height_tmp", String.valueOf(height));

                }
            });

            goodSUOMTable.removeField("id");
            goodSUOMTable.removeField("C5GoodsId");
            goodSUOMTable.removeField("C5UnitId");
            goodSUOMTable.removeField("weight");
            goodSUOMTable.removeField("length");
            goodSUOMTable.removeField("width");
            goodSUOMTable.removeField("height");

            goodSUOMTable.renameField("id_tmp","id");
            goodSUOMTable.renameField("C5GoodsId_tmp","C5GoodsId");
            goodSUOMTable.renameField("C5UnitId_tmp","C5UnitId");
            goodSUOMTable.renameField("weight_tmp","weight");
            goodSUOMTable.renameField("length_tmp","length");
            goodSUOMTable.renameField("width_tmp","width");
            goodSUOMTable.renameField("height_tmp","height");

            goodSUOMTable.addPrimaryKey("id");

            //-------------------------------------------------------------
            RealmObjectSchema goodTypeTable = realmSchema.get("GoodTypeTable");

            assert goodTypeTable != null;
            goodTypeTable.addField("id_tmp", String.class);
            goodTypeTable.addField("typeId_tmp", String.class);
            goodTypeTable.addField("C5GoodsId_tmp", String.class);
            goodTypeTable.addField("GTCode_tmp", String.class);

            goodTypeTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int typeId = obj.getInt("typeId");
                    int C5GoodsId = obj.getInt("C5GoodsId");
                    int GTCode = obj.getInt("GTCode");

                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("typeId_tmp", String.valueOf(typeId));
                    obj.setString("C5GoodsId_tmp", String.valueOf(C5GoodsId));
                    obj.setString("GTCode_tmp", String.valueOf(GTCode));
                }
            });

            goodTypeTable.removeField("id");
            goodTypeTable.removeField("typeId");
            goodTypeTable.removeField("C5GoodsId");
            goodTypeTable.removeField("GTCode");


            goodTypeTable.renameField("id_tmp","id");
            goodTypeTable.renameField("typeId_tmp","typeId");
            goodTypeTable.renameField("C5GoodsId_tmp","C5GoodsId");
            goodTypeTable.renameField("GTCode_tmp","GTCode");

            goodTypeTable.addPrimaryKey("id");
            //-------------------------------------------------------------
            RealmObjectSchema groupRelatedTable = realmSchema.get("GroupRelatedTable");
            assert groupRelatedTable != null;
            groupRelatedTable.addField("id_tmp", String.class);
            groupRelatedTable.addField("B5IdRefId_tmp", String.class);
            groupRelatedTable.addField("groupId_tmp", String.class);
            groupRelatedTable.addField("parentTypeId_tmp", String.class);

            groupRelatedTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int B5IdRefId = obj.getInt("B5IdRefId");
                    int groupId = obj.getInt("groupId");
                    int parentTypeId = obj.getInt("parentTypeId");

                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("B5IdRefId_tmp", String.valueOf(B5IdRefId));
                    obj.setString("groupId_tmp", String.valueOf(groupId));
                    obj.setString("parentTypeId_tmp", String.valueOf(parentTypeId));
                }
            });

                groupRelatedTable.removeField("id");
                groupRelatedTable.removeField("B5IdRefId");
                groupRelatedTable.removeField("groupId");
                groupRelatedTable.removeField("parentTypeId");

            groupRelatedTable.renameField("id_tmp","id");
            groupRelatedTable.renameField("B5IdRefId_tmp","B5IdRefId");
            groupRelatedTable.renameField("groupId_tmp","groupId");
            groupRelatedTable.renameField("parentTypeId_tmp","parentTypeId");


            groupRelatedTable.addPrimaryKey("id");

            //-------------------------------------------------------------
            RealmObjectSchema kartableRecieverTable = realmSchema.get("KartableRecieverTable");
            assert kartableRecieverTable != null;
            kartableRecieverTable.addField("id_tmp", String.class);
            kartableRecieverTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    obj.setString("id_tmp", String.valueOf(id));

                }
            });
            kartableRecieverTable.removeField("id");
            kartableRecieverTable.renameField("id_tmp","id");

            kartableRecieverTable.addPrimaryKey("id");

            //-------------------------------------------------------------
            RealmObjectSchema utilCodeTable = realmSchema.get("UtilCodeTable");

            assert utilCodeTable != null;
            utilCodeTable.addField("id_tmp", String.class);
            utilCodeTable.addField("parentId_tmp", String.class);
            utilCodeTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int parentId = obj.getInt("parentId");
                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("parentId_tmp", String.valueOf(parentId));

                }
            });
            utilCodeTable.removeField("id");
            utilCodeTable.removeField("parentId");

            utilCodeTable.renameField("id_tmp","id");
            utilCodeTable.renameField("parentId_tmp","parentId");

            utilCodeTable.addPrimaryKey("id");
            //-------------------------------------------------------------
            RealmObjectSchema cartableTable = realmSchema.get("CartableTable");
            assert cartableTable != null;
            cartableTable.addField("id_tmp", String.class);
            cartableTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    obj.setString("id_tmp", String.valueOf(id));

                }
            });
            cartableTable.removeField("id");
            cartableTable.renameField("id_tmp","id");

            cartableTable.addPrimaryKey("id");

            //-------------------------------------------------------------
            RealmObjectSchema chatroomMemberTable = realmSchema.get("ChatroomMemberTable");

            assert chatroomMemberTable != null;
            chatroomMemberTable.addField("id_tmp", String.class);
            chatroomMemberTable.addField("U5UserId_tmp", String.class);
            chatroomMemberTable.addField("B5HCUserTypeId_tmp", String.class);
            chatroomMemberTable.addField("G5ChatroomId_tmp", String.class);
            chatroomMemberTable.addField("B5HCStatusId_tmp", String.class);

            chatroomMemberTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int U5UserId = obj.getInt("U5UserId");
                    int B5HCUserTypeId = obj.getInt("B5HCUserTypeId");
                    int G5ChatroomId = obj.getInt("G5ChatroomId");
                    int B5HCStatusId = obj.getInt("B5HCStatusId");

                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("U5UserId_tmp", String.valueOf(U5UserId));
                    obj.setString("B5HCUserTypeId_tmp", String.valueOf(B5HCUserTypeId));
                    obj.setString("G5ChatroomId_tmp", String.valueOf(G5ChatroomId));
                    obj.setString("B5HCStatusId_tmp", String.valueOf(B5HCStatusId));
                }
            });

            chatroomMemberTable.removeField("id");
            chatroomMemberTable.removeField("U5UserId");
            chatroomMemberTable.removeField("B5HCUserTypeId");
            chatroomMemberTable.removeField("G5ChatroomId");
            chatroomMemberTable.removeField("B5HCStatusId");

            chatroomMemberTable.renameField("id_tmp","id");
            chatroomMemberTable.renameField("U5UserId_tmp","U5UserId");
            chatroomMemberTable.renameField("B5HCUserTypeId_tmp","B5HCUserTypeId");
            chatroomMemberTable.renameField("G5ChatroomId_tmp","G5ChatroomId");
            chatroomMemberTable.renameField("B5HCStatusId_tmp","B5HCStatusId");
            //-------------------------------------------------------------
            RealmObjectSchema chatroomTable = realmSchema.get("ChatroomTable");

            assert chatroomTable != null;
            chatroomTable.addField("id_tmp", String.class);
            chatroomTable.addField("B5HCChatTypeId_tmp", String.class);
            chatroomTable.addField("B5HCStatusId_tmp", String.class);

            chatroomTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int B5HCChatTypeId = obj.getInt("B5HCChatTypeId");
                    int B5HCStatusId = obj.getInt("B5HCStatusId");

                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("B5HCChatTypeId_tmp", String.valueOf(B5HCChatTypeId));
                    obj.setString("B5HCStatusId_tmp", String.valueOf(B5HCStatusId));
                }
            });

            chatroomTable.removeField("id");
            chatroomTable.removeField("B5HCChatTypeId");
            chatroomTable.removeField("B5HCStatusId");

            chatroomTable.renameField("id_tmp","id");
            chatroomTable.renameField("B5HCChatTypeId_tmp","B5HCChatTypeId");
            chatroomTable.renameField("B5HCStatusId_tmp","B5HCStatusId");

            //-------------------------------------------------------------
            RealmObjectSchema messageTable = realmSchema.get("MessageTable");


            assert messageTable != null;
            messageTable.addField("id_tmp", String.class);
            messageTable.addField("G5MessageId_tmp", String.class);
            messageTable.addField("G5ChatroomId_tmp", String.class);
            messageTable.addField("B5HCMessageTypeId_tmp", String.class);
            messageTable.addField("B5HCStatusId_tmp", String.class);
            messageTable.addField("B5HCReceiverStatusId_tmp", String.class);
            messageTable.addField("U5UserId_tmp", String.class);

            messageTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {

                    int id = obj.getInt("id");
                    int G5MessageId = obj.getInt("G5MessageId");
                    int G5ChatroomId = obj.getInt("G5ChatroomId");
                    int B5HCMessageTypeId = obj.getInt("B5HCMessageTypeId");
                    int B5HCStatusId = obj.getInt("B5HCStatusId");
                    int B5HCReceiverStatusId = obj.getInt("B5HCReceiverStatusId");
                    int U5UserId = obj.getInt("U5UserId");

                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("G5MessageId_tmp", String.valueOf(G5MessageId));
                    obj.setString("G5ChatroomId_tmp", String.valueOf(G5ChatroomId));
                    obj.setString("B5HCMessageTypeId_tmp", String.valueOf(B5HCMessageTypeId));
                    obj.setString("B5HCStatusId_tmp", String.valueOf(B5HCStatusId));
                    obj.setString("B5HCReceiverStatusId_tmp", String.valueOf(B5HCReceiverStatusId));
                    obj.setString("U5UserId_tmp", String.valueOf(U5UserId));

                }
            });


            messageTable.removeField("A5AttachmentId");//remove with out rename

            messageTable.removeField("id");
            messageTable.removeField("G5MessageId");
            messageTable.removeField("G5ChatroomId");
            messageTable.removeField("B5HCMessageTypeId");
            messageTable.removeField("B5HCStatusId");
            messageTable.removeField("B5HCReceiverStatusId");
            messageTable.removeField("U5UserId");

            messageTable.renameField("id_tmp","id");
            messageTable.renameField("G5MessageId_tmp","G5MessageId");
            messageTable.renameField("G5ChatroomId_tmp","G5ChatroomId");
            messageTable.renameField("B5HCMessageTypeId_tmp","B5HCMessageTypeId");
            messageTable.renameField("B5HCStatusId_tmp","B5HCStatusId");
            messageTable.renameField("B5HCReceiverStatusId_tmp","B5HCReceiverStatusId");
            messageTable.renameField("U5UserId_tmp","U5UserId");


            //-------------------------------------------------------------
            RealmObjectSchema taskTable = realmSchema.get("TaskTable");


            assert taskTable != null;
            taskTable.addField("id_tmp", String.class);
            taskTable.addField("B5IdRefId_tmp", String.class);
            taskTable.addField("B5IdRefId2_tmp", String.class);
            taskTable.addField("B5IdRefId3_tmp", String.class);
            taskTable.addField("T5SCTypeId_tmp", String.class);
            taskTable.addField("B5HCPriorityId_tmp", String.class);
            taskTable.addField("B5HCStatusId_tmp", String.class);

            taskTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int B5IdRefId = obj.getInt("B5IdRefId");
                    int B5IdRefId2 = obj.getInt("B5IdRefId2");
                    int B5IdRefId3 = obj.getInt("B5IdRefId3");
                    int T5SCTypeId = obj.getInt("T5SCTypeId");
                    int B5HCPriorityId = obj.getInt("B5HCPriorityId");
                    int B5HCStatusId = obj.getInt("B5HCStatusId");

                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("B5IdRefId_tmp", String.valueOf(B5IdRefId));
                    obj.setString("B5IdRefId2_tmp", String.valueOf(B5IdRefId2));
                    obj.setString("B5IdRefId3_tmp", String.valueOf(B5IdRefId3));
                    obj.setString("T5SCTypeId_tmp", String.valueOf(T5SCTypeId));
                    obj.setString("B5HCPriorityId_tmp", String.valueOf(B5HCPriorityId));
                    obj.setString("B5HCStatusId_tmp", String.valueOf(B5HCStatusId));
                }
            });

            taskTable.removeField("id");
            taskTable.removeField("B5IdRefId");
            taskTable.removeField("B5IdRefId2");
            taskTable.removeField("B5IdRefId3");
            taskTable.removeField("T5SCTypeId");
            taskTable.removeField("B5HCPriorityId");
            taskTable.removeField("B5HCStatusId");

            taskTable.renameField("id_tmp","id");
            taskTable.renameField("B5IdRefId_tmp","B5IdRefId");
            taskTable.renameField("B5IdRefId2_tmp","B5IdRefId2");
            taskTable.renameField("B5IdRefId3_tmp","B5IdRefId3");
            taskTable.renameField("T5SCTypeId_tmp","T5SCTypeId");
            taskTable.renameField("B5HCPriorityId_tmp","B5HCPriorityId");
            taskTable.renameField("B5HCStatusId_tmp","B5HCStatusId");

            //-------------------------------------------------------------

            RealmObjectSchema timeTable = realmSchema.get("TimeTable");
            assert timeTable != null;
            timeTable.addField("id_tmp", String.class);
            timeTable.addField("B5formRefId_tmp", String.class);
            timeTable.addField("B5IdRefId_tmp", String.class);
            timeTable.addField("B5IdRefId2_tmp", String.class);
            timeTable.addField("B5IdRefId3_tmp", String.class);
            timeTable.addField("B5HCDailyScheduleId_tmp", String.class);
            timeTable.addField("B5HCWageTitleId_tmp", String.class);
            timeTable.addField("B5HCStatusId_tmp", String.class);

            timeTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    int B5formRefId = obj.getInt("B5formRefId");
                    int B5IdRefId = obj.getInt("B5IdRefId");
                    int B5IdRefId2 = obj.getInt("B5IdRefId2");
                    int B5IdRefId3 = obj.getInt("B5IdRefId3");
                    int B5HCDailyScheduleId = obj.getInt("B5HCDailyScheduleId");
                    int B5HCWageTitleId = obj.getInt("B5HCWageTitleId");
                    int B5HCStatusId = obj.getInt("B5HCStatusId");


                    obj.setString("id_tmp", String.valueOf(id));
                    obj.setString("B5formRefId_tmp", String.valueOf(B5formRefId));
                    obj.setString("B5IdRefId_tmp", String.valueOf(B5IdRefId));
                    obj.setString("B5IdRefId2_tmp", String.valueOf(B5IdRefId2));
                    obj.setString("B5IdRefId3_tmp", String.valueOf(B5IdRefId3));
                    obj.setString("B5HCDailyScheduleId_tmp", String.valueOf(B5HCDailyScheduleId));
                    obj.setString("B5HCWageTitleId_tmp", String.valueOf(B5HCWageTitleId));
                    obj.setString("B5HCStatusId_tmp", String.valueOf(B5HCStatusId));

                }
            });


            timeTable.removeField("id");
            timeTable.removeField("B5formRefId");
            timeTable.removeField("B5IdRefId");
            timeTable.removeField("B5IdRefId2");
            timeTable.removeField("B5IdRefId3");
            timeTable.removeField("B5HCDailyScheduleId");
            timeTable.removeField("B5HCWageTitleId");
            timeTable.removeField("B5HCStatusId");

            timeTable.renameField("id_tmp","id");
            timeTable.renameField("B5formRefId_tmp","B5formRefId");
            timeTable.renameField("B5IdRefId_tmp","B5IdRefId");
            timeTable.renameField("B5IdRefId2_tmp","B5IdRefId2");
            timeTable.renameField("B5IdRefId3_tmp","B5IdRefId3");
            timeTable.renameField("B5HCDailyScheduleId_tmp","B5HCDailyScheduleId");
            timeTable.renameField("B5HCWageTitleId_tmp","B5HCWageTitleId");
            timeTable.renameField("B5HCStatusId_tmp","B5HCStatusId");


            //-------------------------------------------------------------
            RealmObjectSchema warehouseHandlingTable = realmSchema.get("WarehouseHandlingTable");
            assert warehouseHandlingTable != null;
            warehouseHandlingTable.addField("id_tmp", String.class);
            warehouseHandlingTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    obj.setString("id_tmp", String.valueOf(id));

                }
            });
            warehouseHandlingTable.removeField("id");
            warehouseHandlingTable.renameField("id_tmp","id");

            warehouseHandlingTable.addPrimaryKey("id");

            //-------------------------------------------------------------
            RealmObjectSchema weighingTable = realmSchema.get("WeighingTable");
            assert weighingTable != null;
            weighingTable.addField("id_tmp", String.class);
            weighingTable.transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(@NonNull DynamicRealmObject obj) {
                    int id = obj.getInt("id");
                    obj.setString("id_tmp", String.valueOf(id));

                }
            });
            weighingTable.removeField("id");
            weighingTable.renameField("id_tmp","id");

            weighingTable.addPrimaryKey("id");

            //-------------------------------------------------------------

            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 2) {

            RealmObjectSchema goodsTable = realmSchema.get("GoodsTable");
            assert goodsTable != null;
            goodsTable.addField("serial",String.class);


            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 3) {

            RealmObjectSchema goodsTable = realmSchema.get("GoodsTable");
            assert goodsTable != null;
            goodsTable.addField("oldValue",String.class);


            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 4){

            RealmObjectSchema goodsTable = realmSchema.get("GoodsTable");
            assert goodsTable != null;
            goodsTable.addField("alloy",String.class);

            RealmObjectSchema goodSUOMTable = realmSchema.get("GoodSUOMTable");
            assert goodSUOMTable != null;
            goodSUOMTable.addField("alloy",String.class);

            RealmObjectSchema goodTranceTable = realmSchema
                    .createWithPrimaryKeyField("GoodTranceTable",
                            "guid",String.class,FieldAttribute.PRIMARY_KEY);

            goodTranceTable.addField("id",String.class);
            goodTranceTable.addField("B5FormRefId",String.class);
            goodTranceTable.addField("B5IdRefId1",String.class);
            goodTranceTable.addField("B5IdRefId2",String.class);
            goodTranceTable.addField("B5IdRefId3",String.class);
            goodTranceTable.addField("B5IdRefId4",String.class);
            goodTranceTable.addField("B5IdRefId5",String.class);
            goodTranceTable.addField("B5IdRefId6",String.class);
            goodTranceTable.addField("B5IdRefId14",String.class);
            goodTranceTable.addField("B5HCCurrencyId",String.class);
            goodTranceTable.addField("B5HCSellMethodId",String.class);
            goodTranceTable.addField("B5HCAccountSideId",String.class);
            goodTranceTable.addField("B5IdRefIdRecall",String.class);
            goodTranceTable.addField("B5HCStatusId",String.class);
            goodTranceTable.addField("deliveryName",String.class);
            goodTranceTable.addField("formNumber",String.class);
            goodTranceTable.addField("insertDate",String.class);
            goodTranceTable.addField("formDate",String.class);
            goodTranceTable.addField("expireDate",String.class);
            goodTranceTable.addField("secondaryDate",String.class);
            goodTranceTable.addField("description",String.class);
            goodTranceTable.addField("syncState",String.class);
            goodTranceTable.addField("activityState",String.class);
            goodTranceTable.addField("oldValue",String.class);
            goodTranceTable.addField("goodTransDetail",String.class);
            goodTranceTable.addField("duration",Integer.class,FieldAttribute.REQUIRED);//because int in table is required and not nullable by default (if we use Integer instead of int ... no need to "required")

            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 5){
            RealmObjectSchema goodTranceTable = realmSchema.get("GoodTranceTable");
            assert goodTranceTable != null;
            goodTranceTable.removeField("insertDate");
            goodTranceTable.removeField("secondaryDate");
            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 6){
            RealmObjectSchema weighingTable = realmSchema.get("WeighingTable");
            assert weighingTable != null;
            weighingTable.addField("B5HCStatusId",String.class);

            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 7){

            RealmObjectSchema salableGoodsTable = realmSchema
                    .createWithPrimaryKeyField("SalableGoodsTable",
                            "guid",String.class,FieldAttribute.PRIMARY_KEY);

            salableGoodsTable.addField("goodColumnNumber",Integer.class,FieldAttribute.REQUIRED);//because int in table is required and not nullable by default (if we use Integer instead of int ... no need to "required")
            salableGoodsTable.addField("id",String.class);
            salableGoodsTable.addField("code",String.class);
            salableGoodsTable.addField("goodCode",String.class);
            salableGoodsTable.addField("techInfo",String.class);
            salableGoodsTable.addField("goodName",String.class);
            salableGoodsTable.addField("unitName1",String.class);
            salableGoodsTable.addField("unitId",String.class);
            salableGoodsTable.addField("unitName2",String.class);
            salableGoodsTable.addField("amount",String.class);
            salableGoodsTable.addField("amount2",String.class);
            salableGoodsTable.addField("code6",String.class);
            salableGoodsTable.addField("name6",String.class);
            salableGoodsTable.addField("formNumber",String.class);
            salableGoodsTable.addField("formDate",String.class);
            salableGoodsTable.addField("description",String.class);
            salableGoodsTable.addField("length",String.class);
            salableGoodsTable.addField("width",String.class);
            salableGoodsTable.addField("height",String.class);
            salableGoodsTable.addField("alloy",String.class);
            salableGoodsTable.addField("expireDate",String.class);
            salableGoodsTable.addField("B5IdRefId15",String.class);
            salableGoodsTable.addField("B5IdRefId8",String.class);
            salableGoodsTable.addField("B5IdRefId9",String.class);
            salableGoodsTable.addField("B5IdRefId10",String.class);
            salableGoodsTable.addField("statusName",String.class);
            salableGoodsTable.addField("formName",String.class);
            salableGoodsTable.addField("yearCode",String.class);
            salableGoodsTable.addField("goodId",String.class);
            salableGoodsTable.addField("syncState",String.class);
            salableGoodsTable.addField("activityState",String.class);
            salableGoodsTable.addField("oldValue",String.class);
            salableGoodsTable.addField("attach",String.class);

            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 8){
            realmSchema.remove("GoodSUOMTable");
            realmSchema.remove("GoodTypeTable");
            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 9){
            RealmObjectSchema goodsTable = realmSchema.get("GoodsTable");

            assert goodsTable != null;
            goodsTable.addField("goodSUOMList",String.class);
            goodsTable.addField("goodTypeList",String.class);

            goodsTable.removeField("C5UnitId");
            goodsTable.removeField("width");
            goodsTable.removeField("height");
            goodsTable.removeField("length");
            goodsTable.removeField("weight");
            goodsTable.removeField("alloy");
            goodsTable.removeField("barCode");
            goodsTable.removeField("unitName");

            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 10){
             realmSchema.rename("SalableGoodsTable","PurchasableGoodsTable");
            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 11){
            RealmObjectSchema salableGoodsTable = realmSchema
                    .createWithPrimaryKeyField("SalableGoodsTable",
                            "guid",String.class,FieldAttribute.PRIMARY_KEY);

            salableGoodsTable.addField("goodColumnNumber",Integer.class,FieldAttribute.REQUIRED);//because int in table is required and not nullable by default (if we use Integer instead of int ... no need to "required")
            salableGoodsTable.addField("id",String.class);
            salableGoodsTable.addField("customerGroupId",String.class);
            salableGoodsTable.addField("goodId",String.class);
            salableGoodsTable.addField("code",String.class);
            salableGoodsTable.addField("goodCode",String.class);
            salableGoodsTable.addField("techInfo",String.class);
            salableGoodsTable.addField("goodName",String.class);
            salableGoodsTable.addField("unitName",String.class);
            salableGoodsTable.addField("unitId2",String.class);
            salableGoodsTable.addField("unitName2",String.class);
            salableGoodsTable.addField("description",String.class);
            salableGoodsTable.addField("unitPrice",String.class);
            salableGoodsTable.addField("length",String.class);
            salableGoodsTable.addField("width",String.class);
            salableGoodsTable.addField("height",String.class);
            salableGoodsTable.addField("alloy",String.class);
            salableGoodsTable.addField("goodBrandId",String.class);
            salableGoodsTable.addField("discountPercentage",String.class);
            salableGoodsTable.addField("taxPercentage",String.class);
            salableGoodsTable.addField("availableAmount2",String.class);
            salableGoodsTable.addField("numerator",String.class);
            salableGoodsTable.addField("denominator",String.class);
            salableGoodsTable.addField("baseAmountForGift",String.class);
            salableGoodsTable.addField("goodsIdGift",String.class);
            salableGoodsTable.addField("giftAmount",String.class);
            salableGoodsTable.addField("expireDate",String.class);
            salableGoodsTable.addField("syncState",String.class);
            salableGoodsTable.addField("activityState",String.class);
            salableGoodsTable.addField("oldValue",String.class);

            oldVersion++; //for go to next step
        }
//--------------------------------------------------------------------------------
        if (oldVersion == 12){
            RealmObjectSchema goodTranceTable = realmSchema.get("GoodTranceTable");
            assert goodTranceTable != null;
            goodTranceTable.addField("addressId",String.class);
            goodTranceTable.addField("customerGroupId",String.class);

            oldVersion++; //for go to next step
        }*/

    }
}
