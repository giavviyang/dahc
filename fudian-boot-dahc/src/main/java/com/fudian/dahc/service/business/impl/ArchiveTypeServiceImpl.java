package com.fudian.dahc.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.CommonStatus;
import com.fudian.dahc.mapper.business.DahcArchiveTypeMapper;
import com.fudian.dahc.mapper.business.DahcMapperMapper;
import com.fudian.dahc.mapper.business.DahcModelMapper;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndMetadataDto;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndMetadataVo;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndModelVo;
import com.fudian.dahc.pojo.dto.DahcMetadataVo;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.business.DahcBusinessArchiveMetadata;
import com.fudian.dahc.pojo.entity.business.DahcBusinessMapper;
import com.fudian.dahc.pojo.entity.business.DahcModel;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictData;
import com.fudian.dahc.pojo.query.ArchiveAndMetadataDto;
import com.fudian.dahc.pojo.query.ArchiveAndMetadataVO;
import com.fudian.dahc.pojo.query.ArchiveTypeQuery;
import com.fudian.dahc.service.base.impl.MyBaseServiceImpl;
import com.fudian.dahc.service.business.ArchiveTypeService;
import com.fudian.dahc.service.sys.IDahcSysDictDataService;
import com.fudian.dahc.util.common.AssertUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 2023/1/30
 *
 * @author fudian
 */
@Service
public class ArchiveTypeServiceImpl extends MyBaseServiceImpl<DahcArchiveType> implements ArchiveTypeService {

    @Autowired
    private IDahcSysDictDataService dahcSysDictData;


    private final DahcArchiveTypeMapper dahcArchiveTypeMapper;

    private final DahcModelMapper dahcModelMapper;


    public ArchiveTypeServiceImpl(DahcArchiveTypeMapper dahcArchiveTypeMapper, DahcModelMapper dahcModelMapper) {
        this.dahcArchiveTypeMapper = dahcArchiveTypeMapper;
        this.dahcModelMapper = dahcModelMapper;
    }


    @Override
    public List<DahcArchiveType> searchPage(ArchiveTypeQuery archiveTypeQuery) {
        MPJLambdaWrapper<DahcArchiveType> lambda = new MPJLambdaWrapper<>();
        // 如果有条件，就按照条件查询
        lambda.select("t.*,s1.nick_name as createByName,s2.nick_name as updateByName");
        lambda.like(StringUtils.hasText(archiveTypeQuery.getArchiveTypeName()), DahcArchiveType::getArchiveName, archiveTypeQuery.getArchiveTypeName());
        lambda.like(StringUtils.hasText(archiveTypeQuery.getArchiveTypeDesc()), DahcArchiveType::getArchiveDesc, archiveTypeQuery.getArchiveTypeDesc());
        lambda.between(Objects.nonNull(archiveTypeQuery.getStartTime()), DahcArchiveType::getCreateTime, archiveTypeQuery.getStartTime(), archiveTypeQuery.getEndTime());
        lambda.leftJoin("sys_user s1 on  t.create_by =s1.user_id ");
        lambda.leftJoin("sys_user s2 on  t.update_by =s2.user_id ");
        lambda.orderByAsc(DahcArchiveType::getCreateTime);
        // 没有条件，就查询全部
        return dahcArchiveTypeMapper.selectList(lambda);
    }


    @Override
    public List<DahcArchiveTypeAndMetadataVo> selectListAndMetadataById(Integer level, Long id) {
        List<DahcSysDictData> list = new ArrayList<>();
        if (level == 1) {
            /*获取案卷默认字段*/
            list = dahcSysDictData.list(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getDictType, "101").eq(DahcSysDictData::getType, 0));
        } else {
            /*获取案件默认字段*/
            list = dahcSysDictData.list(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getDictType, "101").eq(DahcSysDictData::getType, 1));
        }
        List<DahcArchiveTypeAndMetadataVo> dahcArchiveTypeAndMetadataVos = dahcArchiveTypeMapper.selectListAndMetadataById(level, id);
        if (dahcArchiveTypeAndMetadataVos.size() > 0) {
            if (dahcArchiveTypeAndMetadataVos.get(0).getDahcMetadataList().size() > 0) {
                for (DahcMetadataVo dahcMetadataVo : dahcArchiveTypeAndMetadataVos.get(0).getDahcMetadataList()) {
                    boolean present = list.stream().filter(data -> data.getCodeProperty().equals(dahcMetadataVo.getMetadataId())).findAny().isPresent();
                    if (present) {
                        dahcMetadataVo.setDisabled(true);
                    }
                }

            }
        }
        return dahcArchiveTypeAndMetadataVos;
    }


    @Override
    public CommonResult<List<ArchiveAndMetadataDto>> queryArchiveAndMetadata(ArchiveAndMetadataDto archiveAndMetadataDto) {
        List<ArchiveAndMetadataDto> archiveAndMetadataDtos = dahcArchiveTypeMapper.queryArchiveAndMetadata(archiveAndMetadataDto.getArchiveLevelName());
        return CommonResult.success(archiveAndMetadataDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertMetadataList(ArchiveAndMetadataVO archiveAndMetadataVO) {
        /*判断字段是否对应*/
        /*案卷，验证模版是否有数据*/
        List<DahcArchiveTypeAndMetadataDto> metadataDtoList = archiveAndMetadataVO.getMetadataDtos();
        /*查询模版绑定数据*/
        List<DahcBusinessMapper> dahcBusinessMapperList = dahcModelMapper.queryTemplateBindingData(archiveAndMetadataVO.getArchiveTypeId(), archiveAndMetadataVO.getArchiveLevelName());

        Map<String, DahcArchiveTypeAndMetadataDto> personMap = metadataDtoList.stream()
                .collect(Collectors.toMap(DahcArchiveTypeAndMetadataDto::getMetadataName, p -> p, (p1, p2) -> p1));

        List<DahcBusinessMapper> collect = dahcBusinessMapperList.stream().filter(item -> !personMap.containsKey(item.getMetadataName())).collect(Collectors.toList());
        //List<DahcArchiveTypeAndMetadataDto> collect3 = collect.stream().filter(item -> metadataDtoList.stream().allMatch(each -> !item.equals(each))).collect(Collectors.toList());
        if (collect.size() > 0) {
            throw new RuntimeException("模版引用：" + collect.get(0).getMetadataName() + "字段，请先删除模版关联字段");
        }
        //AssertUtil.isTrueServiceInvoke(collect.size() >= 0, CommonStatus.ERROR, "模版引用："+collect.get(0).getMetadataName()+"字段，请先删除模版关联字段");

        AssertUtil.isTrueServiceInvoke(
                dahcArchiveTypeMapper.queryWhetherThereIsData(archiveAndMetadataVO.getArchiveLevelName()) == 0,
                CommonStatus.ERROR, "此档案已导入数据,禁止添加或修改字段");
        //获取用户上传的元数据集合
        List<DahcArchiveTypeAndMetadataDto> metadataDtos = archiveAndMetadataVO.getMetadataDtos();
        Map<String, DahcArchiveTypeAndMetadataDto> collect1 = metadataDtos.stream().collect(Collectors.toMap(dahcArchiveTypeAndMetadataDto -> {
            Long metadataId = dahcArchiveTypeAndMetadataDto.getMetadataId();
            return String.valueOf(metadataId);
        }, Function.identity()));
        //查询之前的元数据集合
        List<ArchiveAndMetadataDto> archiveAndMetadataDtos = dahcArchiveTypeMapper.queryArchiveAndMetadata(archiveAndMetadataVO.getArchiveLevelName());
        if (!archiveAndMetadataDtos.isEmpty()) {
            Map<String, ArchiveAndMetadataDto> collect2 = archiveAndMetadataDtos.stream().collect(Collectors.toMap(ArchiveAndMetadataDto::getMetadataId, Function.identity()));
            MapDifference<String, Object> difference = Maps.difference(collect1, collect2);

            //获取之前元数据集合独有的元数据
            Map<String, Object> map = difference.entriesOnlyOnRight();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                ArchiveAndMetadataDto mapValue = (ArchiveAndMetadataDto) entry.getValue();
                String archiveLevelName = mapValue.getArchiveLevelName();
                if (archiveLevelName.startsWith("dahc_dt1")) {
                    //获取默认字段
                    List<DahcSysDictData> list = dahcSysDictData.list(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getDictType, "101").eq(DahcSysDictData::getType, 0).orderByDesc(DahcSysDictData::getCreateTime));
                    AssertUtil.isTrueServiceInvoke(!key.equals(list.get(0).getCodeProperty()), CommonStatus.ERROR, list.get(0).getFullName()+"是案卷的默认字段,请不要进行删除");
                } else if (archiveLevelName.startsWith("dahc_dt2")) {
                    List<DahcSysDictData> list = dahcSysDictData.list(Wrappers.<DahcSysDictData>lambdaQuery().eq(DahcSysDictData::getDictType, "101").eq(DahcSysDictData::getType, 0).orderByDesc(DahcSysDictData::getCreateTime));
                    AssertUtil.isTrueServiceInvoke(!key.equals(list.get(0).getCodeProperty()) && !key.equals(list.get(1).getCodeProperty()), CommonStatus.ERROR, list.get(0).getFullName()+"是文件的默认字段,请不要进行删除");
                }
                dahcArchiveTypeMapper.deleteMetadataOfArchive(archiveAndMetadataVO.getArchiveTypeId(), mapValue.getMetadataId());
            }
        }
        //插入元数据
        int i1 = dahcArchiveTypeMapper.saveMetadataInBulk(archiveAndMetadataVO);


        /*查询当前档案类型关联的元数据字段，获取当前的表的attr序号，并修改模版的序号*/
        List<DahcBusinessArchiveMetadata> dahcBusinessArchiveMetadata = dahcArchiveTypeMapper.theAssociatedMetadataField(archiveAndMetadataVO.getArchiveTypeId(), archiveAndMetadataVO.getArchiveLevelName());
        List<String> modelIds = dahcModelMapper.selectList(Wrappers.<DahcModel>lambdaQuery()
                .eq(DahcModel::getArchiveTypeId, archiveAndMetadataVO.getArchiveTypeId()).eq(DahcModel::getArchiveTableName, archiveAndMetadataVO.getArchiveLevelName()))
                .stream().map(DahcModel::getId).collect(Collectors.toList());
        int i = dahcArchiveTypeMapper.modifyTemplateSerialNumbersInBulk(dahcBusinessArchiveMetadata, String.join(",", modelIds));


        return i1;
    }


    /**
     * 删除一个档案类型所有元数据关联
     * 根据档案类型id 或者 表名称进行删除
     *
     * @param archiveAndMetadataVO
     */
    public int deleteListAndMetadataAll(ArchiveAndMetadataVO archiveAndMetadataVO) {
        if (!StringUtils.isEmpty(archiveAndMetadataVO.getArchiveTypeId()) || !StringUtils.isEmpty(archiveAndMetadataVO.getArchiveLevelName())) {
            return dahcArchiveTypeMapper.deleteListAndMetadataAll(archiveAndMetadataVO);
        }
        return -1;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(DahcArchiveType dahcArchiveType) {
        int i = dahcArchiveTypeMapper.selectCountDuplicateChecking(dahcArchiveType);
        AssertUtil.isTrueServiceInvoke(i == 0, CommonStatus.FORBIDDEN, "重复的档案类型,请重命名");
        String s = RandomStringUtils.randomAlphabetic(20);
        String tableLevel1En = "dahc_dt1_" + s;
        String tableLevel2En = "dahc_dt2_" + s;
        dahcArchiveType.setTableLevel1En(tableLevel1En);
        dahcArchiveType.setTableLevel2En(tableLevel2En);
        //创建案卷级数据存储表
        dahcArchiveTypeMapper.createDateTemplateTable(tableLevel1En);
        //创建案卷级数据存储表备份表
        originalDataStorageTable(tableLevel1En);
        //创建文件级数据存储表
        dahcArchiveTypeMapper.createDateTemplateTable(tableLevel2En);
        //创建文件级数据存储表备份表
        originalDataStorageTable(tableLevel2En);
        int add = super.add(dahcArchiveType);
        String id1 = dahcArchiveType.getId();
        /*添加默认字段*/
        addAnArchiveTypeDefaultField(id1, tableLevel1En, tableLevel2En);
        return add;
    }

    /*添加档案类型默认字段*/
    private void addAnArchiveTypeDefaultField(String archiveTypeId, String tableLevel1En, String tableLevel2En) {
        ArrayList<DahcBusinessArchiveMetadata> archiveMetadataList = new ArrayList<>();
        /*案卷级*/
        List<DahcSysDictData> dahcSysDictData = this.dahcSysDictData.list(Wrappers.<DahcSysDictData>lambdaQuery()
                .eq(DahcSysDictData::getDictType, "101")
                .eq(DahcSysDictData::getType, 0));
        if (dahcSysDictData.size() > 0) {
            int i = 40;
            for (DahcSysDictData sysDictData : dahcSysDictData) {
                i = i - 1;
                archiveMetadataList.add(processData(archiveTypeId, tableLevel1En, sysDictData.getCodeProperty(), i));
            }
        }
        /*案件级*/
        List<DahcSysDictData> sysDictDataCaseLevel = this.dahcSysDictData.list(Wrappers.<DahcSysDictData>lambdaQuery()
                .eq(DahcSysDictData::getDictType, "101")
                .eq(DahcSysDictData::getType, 1));
        if (sysDictDataCaseLevel.size() > 0) {
            int i = 40;
            for (DahcSysDictData sysDictData : sysDictDataCaseLevel) {
                i = i - 1;
                archiveMetadataList.add(processData(archiveTypeId, tableLevel2En, sysDictData.getCodeProperty(), i));
            }
        }

        dahcArchiveTypeMapper.addDefaultDataToTheProfileTypeInBulk(archiveMetadataList);
    }

    /*处理数据*/
    private DahcBusinessArchiveMetadata processData(String archiveTypeId, String tableLevel1En, String metadataId, int i) {
        DahcBusinessArchiveMetadata archiveMetadata = new DahcBusinessArchiveMetadata();
        archiveMetadata.setArchiveTypeId(archiveTypeId);
        archiveMetadata.setArchiveLevelName(tableLevel1En);
        archiveMetadata.setMetadataId(metadataId);
        archiveMetadata.setAttrOrder(String.valueOf(i));
        return archiveMetadata;
    }

    @Override
    public int update(DahcArchiveType dahcArchiveType) {
        int i = dahcArchiveTypeMapper.selectCountDuplicateChecking(dahcArchiveType);
        AssertUtil.isTrueServiceInvoke(i == 0, CommonStatus.NON_COMPLIANCE_WITH_REQUIREMENTS, "重复的档案,请重命名");

        return super.update(dahcArchiveType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        //查询符合条件的模板
        MPJLambdaWrapper<DahcModel> wrapper = new MPJLambdaWrapper<>();
        wrapper.eq(DahcArchiveType::getId, id);
        wrapper.rightJoin(DahcArchiveType.class, DahcArchiveType::getId, DahcModel::getArchiveTypeId);
        Long aLong = dahcModelMapper.selectCount(wrapper);
        AssertUtil.isTrueServiceInvoke(aLong == 0, CommonStatus.METHOD_NOT_ALLOWED, "存在关联模版");
        DahcArchiveType byId = findById(id);
        //根据档案id删除案卷级数据存储表
        dahcArchiveTypeMapper.deleteTable(byId.getTableLevel1En());
        //根据档案id删除案卷级数据存储表备份表
        dahcArchiveTypeMapper.deleteTable(byId.getTableLevel1En() + "_backup");
        //根据档案id删除文件级数据存储表
        dahcArchiveTypeMapper.deleteTable(byId.getTableLevel2En());
        //根据档案id删除文件级数据存储表备份表
        dahcArchiveTypeMapper.deleteTable(byId.getTableLevel2En() + "_backup");
        return super.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids ids
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDeleteByIds(List<Long> ids) {
        //查询符合条件的模板
        MPJLambdaWrapper<DahcModel> wrapper = new MPJLambdaWrapper<>();
        wrapper.in(DahcArchiveType::getId, ids);
        wrapper.eq(DahcModel::getPid, 0);
        wrapper.leftJoin(DahcArchiveType.class, DahcArchiveType::getId, DahcModel::getArchiveTypeId);
        Long aLong = dahcModelMapper.selectCount(wrapper);
        AssertUtil.isTrueServiceInvoke(aLong == 0, CommonStatus.METHOD_NOT_ALLOWED, "存在关联模版,请删除相关模版后再操作");
        List<DahcArchiveType> dahcArchiveTypes = dahcArchiveTypeMapper.selectBatchIds(ids);
        Map<String, DahcArchiveType> collect = dahcArchiveTypes.stream().collect(Collectors.toMap(DahcArchiveType::getId, a -> a));
        ids.parallelStream().forEach(id -> {
            if (collect.containsKey(String.valueOf(id))) {
                DahcArchiveType dahcArchiveType = collect.get(String.valueOf(id));
                //根据档案id删除案卷级数据存储表
                dahcArchiveTypeMapper.deleteTable(dahcArchiveType.getTableLevel1En());
                //根据档案id删除案卷级数据存储表备份表
                dahcArchiveTypeMapper.deleteTable(dahcArchiveType.getTableLevel1En() + "_backup");
                //根据档案id删除文件级数据存储表
                dahcArchiveTypeMapper.deleteTable(dahcArchiveType.getTableLevel2En());
                //根据档案id删除文件级数据存储表备份表
                dahcArchiveTypeMapper.deleteTable(dahcArchiveType.getTableLevel2En() + "_backup");
            }
            ArchiveAndMetadataVO archiveAndMetadataVO = new ArchiveAndMetadataVO();
            archiveAndMetadataVO.setArchiveTypeId(id);
            deleteListAndMetadataAll(archiveAndMetadataVO);
        });


        return super.batchDeleteByIds(ids);
    }


    @Override
    @Transactional
    public CommonResult batchDeleteByIdsArchiveType(List<Long> ids) {

        /*查询是否存在关联的项目数据*/
        List<DahcArchiveTypeAndModelVo> projects = dahcArchiveTypeMapper.queryWhetherThereIsAssociatedProjectData(ids);
        AssertUtil.isTrueServiceInvoke(projects.isEmpty(),
                CommonStatus.ERROR, "档案存在项目引用,请先删除项目再操作");
        //查询符合条件的模板
        MPJLambdaWrapper<DahcModel> wrapper = new MPJLambdaWrapper<>();
        wrapper.in(DahcArchiveType::getId, ids);
        wrapper.eq(DahcModel::getPid, 0);
        wrapper.leftJoin(DahcArchiveType.class, DahcArchiveType::getId, DahcModel::getArchiveTypeId);
        Long aLong = dahcModelMapper.selectCount(wrapper);
        AssertUtil.isTrueServiceInvoke(aLong <= 0, CommonStatus.ERROR, "存在关联模版,请删除所有模版后再操作");
        List<DahcArchiveType> dahcArchiveTypes = dahcArchiveTypeMapper.selectBatchIds(ids);
        Map<String, DahcArchiveType> collect = dahcArchiveTypes.stream().collect(Collectors.toMap(DahcArchiveType::getId, a -> a));
        ids.parallelStream().forEach(id -> {
            if (collect.containsKey(String.valueOf(id))) {
                DahcArchiveType dahcArchiveType = collect.get(String.valueOf(id));
                //根据档案id删除案卷级数据存储表
                dahcArchiveTypeMapper.deleteTable(dahcArchiveType.getTableLevel1En());
                //根据档案id删除案卷级数据存储表备份表
                dahcArchiveTypeMapper.deleteTable(dahcArchiveType.getTableLevel1En() + "_backup");
                //根据档案id删除文件级数据存储表
                dahcArchiveTypeMapper.deleteTable(dahcArchiveType.getTableLevel2En());
                //根据档案id删除文件级数据存储表备份表
                dahcArchiveTypeMapper.deleteTable(dahcArchiveType.getTableLevel2En() + "_backup");
            }
            ArchiveAndMetadataVO archiveAndMetadataVO = new ArchiveAndMetadataVO();
            archiveAndMetadataVO.setArchiveTypeId(id);
            deleteListAndMetadataAll(archiveAndMetadataVO);
        });
        int i = super.batchDeleteByIds(ids);
        AssertUtil.isTrueServiceInvoke(i > 0, CommonStatus.ERROR, "批量删除失败");
        return CommonResult.success();
    }

    /**
     * **查询树状模板 可不加id查询所有档案
     *
     * @param id 档案类型id
     * @return
     */
    @Override
    public CommonResult<List<DahcArchiveTypeAndModelVo>> selectModelByArchiveType(String id) {
        List<DahcArchiveTypeAndModelVo> dahcArchiveTypeList = dahcArchiveTypeMapper.queryFileTypeAndTemplate(id);
        //查询符合条件的模板
        MPJLambdaWrapper<DahcModel> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(DahcModel.class);
        if (id != null) {
            wrapper.eq(DahcArchiveType::getId, id);
            wrapper.rightJoin(DahcArchiveType.class, DahcArchiveType::getId, DahcModel::getArchiveTypeId);
        }
        wrapper.orderByAsc(DahcModel::getArchiveTypeId);
        List<DahcModel> dahcModels = dahcModelMapper.selectList(wrapper);
        //过滤pid为0的模板
        List<DahcModel> modelLv1 = dahcModels.stream().filter(a -> "0".equals(a.getPid())).collect(Collectors.toList());
        dahcArchiveTypeList.forEach(dahcArchiveTypeAndModelVo -> {
            //过滤获取符合条件的model
            List<DahcModel> collect = modelLv1.stream().filter(model -> model.getArchiveTypeId().equals(dahcArchiveTypeAndModelVo.getId())).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                dahcArchiveTypeAndModelVo.setDahcModelLv(new ArrayList<>());
                collect.forEach(dahcModel -> {
                    List<DahcModel> collect2 = dahcModels.stream().filter(a -> a.getPid().equals(dahcModel.getId())).collect(Collectors.toList());
                    dahcModel.setDahcModelLv(collect2);
                });
                dahcArchiveTypeAndModelVo.setDahcModelLv(collect);
            }
        });
        return CommonResult.success(dahcArchiveTypeList);
    }

    /**
     * 下拉框类型转换
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author MCY
     * @date 2023/2/20 10:34
     */
    @Override
    public CommonResult queryArchiveTransition() {
        List<Map<String, String>> listMap = null;
        try {
            List<DahcArchiveType> list = dahcArchiveTypeMapper.selectList(null);
            listMap = new ArrayList<>();
            for (DahcArchiveType dahcArchiveType : list) {
                Map<String, String> map = new HashMap<>();
                map.put("label", dahcArchiveType.getArchiveName());
                map.put("value", dahcArchiveType.getId());
                listMap.add(map);
            }
            return CommonResult.success(listMap);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(e.getMessage());
        }
    }

    /**
     * 创建备份原始数据存储表
     *
     * @param tableName
     */
    @Override
    public void originalDataStorageTable(String tableName) {
        String originalTableName = tableName + "_backup";
        dahcArchiveTypeMapper.originalDataStorageTable(tableName, originalTableName);
    }

    @Override
    public CommonResult theFileNumberFieldIsThatDatabaseField(String archiveTypeId) {
        return CommonResult.success(dahcArchiveTypeMapper.theFileNumberFieldIsThatDatabaseField(archiveTypeId, "档号"));
    }

    @Override
    public CommonResult caseFileNumberAttr(String archiveTypeId) {
        return CommonResult.success(dahcArchiveTypeMapper.caseFileNumberAttr(archiveTypeId, "档号"));
    }

    /**
     * 根据档案类型获取案卷表名
     *
     * @return java.util.List<com.fudian.dahc.pojo.dto.DahcMetadataVo>
     * @author MCY
     * @date 2023/3/21 16:21
     */
    @Override
    public String getsTheFileTableNameBasedOnTheFileType(String projectId) {
        return dahcArchiveTypeMapper.getsTheFileTableNameBasedOnTheFileType(projectId);
    }

    @Override
    public DahcArchiveType getOne(String archiveTypeId) {
        return dahcArchiveTypeMapper.selectOne(Wrappers.<DahcArchiveType>lambdaQuery().eq(DahcArchiveType::getId, archiveTypeId));
    }

    @Override
    public String queryProfileDefaultFieldBindingAttr(String archiveTypeId, String archiveLevelName, String metadataName) {
        return dahcArchiveTypeMapper.queryProfileDefaultFieldBindingAttr(archiveTypeId, archiveLevelName, metadataName);
    }

}



