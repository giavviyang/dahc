package com.fudian.dahc.service.log.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.utils.DateUtils;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.dahc.mapper.log.DahcCheckRecordLogMapper;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.log.DahcCheckRecordLog;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedure;
import com.fudian.dahc.pojo.entity.project.DahcProjectTable;
import com.fudian.dahc.service.business.ArchiveTypeService;
import com.fudian.dahc.service.log.IDahcCheckRecordLogService;
import com.fudian.dahc.service.project.ProjectProcedureService;
import com.fudian.dahc.service.project.ProjectTableService;
import jdk.nashorn.api.scripting.ScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fudian
 * @date 2023-05-24
 */
@Service
public class DahcCheckRecordLogServiceImpl implements IDahcCheckRecordLogService
{
    @Autowired
    private DahcCheckRecordLogMapper dahcCheckRecordLogMapper;

    @Autowired
    private ProjectTableService projectTableService;

    @Autowired
    private ArchiveTypeService archiveTypeService;

    @Autowired
    private ProjectProcedureService projectProcedureService;
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public DahcCheckRecordLog selectDahcCheckRecordLogById(String id)
    {
        return dahcCheckRecordLogMapper.selectDahcCheckRecordLogById(id);
    }

    /*查询log所需要的数据*/
    @Override
    public DahcCheckRecordLog queryCommonData(String projectId, String procedureId,boolean checkStatus) {
        DahcProjectTable projectTable = projectTableService.getOne(Wrappers.<DahcProjectTable>lambdaQuery().eq(DahcProjectTable::getId, projectId));
        DahcArchiveType archiveType = archiveTypeService.getOne(projectTable.getArchiveTypeId());
        DahcProjectProcedure projectProcedure = projectProcedureService.getOne(Wrappers.<DahcProjectProcedure>lambdaQuery().eq(DahcProjectProcedure::getId, procedureId));
        return new DahcCheckRecordLog(null, projectTable.getProjectName(), projectId,
                projectProcedure.getProcedureName(), procedureId, archiveType.getTableLevel1En(), archiveType.getTableLevel2En(),
                null, null, null, null, null,null,null,null,checkStatus == true ? 1 : 0
        );
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcCheckRecordLog 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DahcCheckRecordLog> selectDahcCheckRecordLogList(DahcCheckRecordLog dahcCheckRecordLog)
    {
        LambdaQueryWrapper<DahcCheckRecordLog> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(dahcCheckRecordLog.getButtonName())) {
            queryWrapper.like(DahcCheckRecordLog::getButtonName, dahcCheckRecordLog.getButtonName());
        }
        if (StringUtils.isNotEmpty(dahcCheckRecordLog.getProjectId())) {
            queryWrapper.like(DahcCheckRecordLog::getProjectId, dahcCheckRecordLog.getProjectId());
        }
        return dahcCheckRecordLogMapper.selectDahcCheckRecordLogList(dahcCheckRecordLog);
        //return dahcCheckRecordLogMapper.selectList(queryWrapper);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param dahcCheckRecordLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDahcCheckRecordLog(DahcCheckRecordLog dahcCheckRecordLog)
    {
        dahcCheckRecordLog.setId(IdWorker.getIdStr());
        dahcCheckRecordLog.setCreateTime(new Date());
        dahcCheckRecordLog.setCreateBy(SecurityUtils.getUsername());
        if (StringUtils.isEmpty(dahcCheckRecordLog.getCaseNumber2()) || dahcCheckRecordLog.getCaseNumber2() == null) {
            dahcCheckRecordLog.setCaseNumber2(" ");
        }
        if (StringUtils.isEmpty(dahcCheckRecordLog.getSourceDataJson()) || dahcCheckRecordLog.getSourceDataJson() == null) {
            dahcCheckRecordLog.setSourceDataJson(" ");
        }

        if (StringUtils.isEmpty(dahcCheckRecordLog.getCaseFileNumber1()) || dahcCheckRecordLog.getCaseFileNumber1() == null) {
            dahcCheckRecordLog.setCaseFileNumber1(" ");
        }
        return dahcCheckRecordLogMapper.insert(dahcCheckRecordLog);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcCheckRecordLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDahcCheckRecordLog(DahcCheckRecordLog dahcCheckRecordLog)
    {
        return dahcCheckRecordLogMapper.updateDahcCheckRecordLog(dahcCheckRecordLog);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDahcCheckRecordLogByIds(String[] ids)
    {
        return dahcCheckRecordLogMapper.deleteDahcCheckRecordLogByIds(ids);
    }

    @Override
    public int removeAll() {
        return dahcCheckRecordLogMapper.delete(null);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDahcCheckRecordLogById(String id)
    {
        return dahcCheckRecordLogMapper.deleteDahcCheckRecordLogById(id);
    }
}
