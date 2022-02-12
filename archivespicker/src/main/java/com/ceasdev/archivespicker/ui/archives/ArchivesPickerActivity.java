package com.ceasdev.archivespicker.ui.archives;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.ceasdev.archivespicker.PickerPreferences;
import com.ceasdev.archivespicker.R;
import com.ceasdev.archivespicker.data.models.Archive;
import com.ceasdev.archivespicker.data.models.Directory;
import com.ceasdev.archivespicker.data.models.StateListArchives;
import com.ceasdev.archivespicker.exceptions.NotFoundPreferencesException;
import com.ceasdev.archivespicker.ui.archives.adapters.ArchiveAdapter;
import com.ceasdev.archivespicker.ui.archives.adapters.DirectoryAdapter;
import com.ceasdev.archivespicker.ui.base.BaseActivity;
import com.ceasdev.archivespicker.utils.StringUtil;
import java.io.File;
import java.util.List;
import android.view.View.OnClickListener;
import android.app.Activity;
import java.util.Arrays;
import java.util.ArrayList;
import com.ceasdev.archivespicker.utils.ResourceUtil;

public class ArchivesPickerActivity extends BaseActivity<ArchivesPickerViewBinding, ArchivesPickerViewModel> implements ArchiveAdapter.OnArchiveListener, DirectoryAdapter.OnDirectoryListener{

    private ArchiveAdapter archiveAdapter;
    private DirectoryAdapter directoryAdapter;
    private Runnable runnableShowProgress;
    private MenuItem menuHidden, menuClose;


    /**Activity**/

    @Override
    public void onBackPressed(){
        if(viewModel.isLoadingDiretory()) return;
        if(!returnDiretory()) super.onBackPressed(); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_toolbar_archives_activity, menu);
        menuHidden = menu.getItem(0);
        menuClose = menu.getItem(1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        final int menuId = item.getItemId();
        if(viewModel.isLoadingDiretory()) return false;
        if(menuId == R.id.menu_hidden_files){
            viewModel.changeHiddenFiles();
            viewModel.notifyDirectory();
            return true;
        }else if(menuId == R.id.menu_close){
            if(archiveAdapter != null && archiveAdapter.getCountSelectedFiles() > 0)
                archiveAdapter.unSelectAll();
            else{
                setResult(Activity.RESULT_CANCELED); 
                finish();
            }
        }
        return false;
    }




    /**Builds**/

    @Override
    protected ArchivesPickerViewBinding buidViewBinding(LayoutInflater layoutInflater){
        return new ArchivesPickerViewBinding(layoutInflater);
    }

    @Override
    protected ArchivesPickerViewModel buildViewModel(Intent intent){
        PickerPreferences preferences = intent.getParcelableExtra(PickerPreferences.KEY);
        if(preferences == null) throw new NotFoundPreferencesException();
        return new ArchivesPickerViewModelFactory(preferences).create(ArchivesPickerViewModel.class);
    }


    /**ArchiveAdapterListener**/

    @Override
    public void onArchiveClick(Archive archive, int position){
        if(viewModel.isLoadingDiretory()) return;
        if(archive.isDirectory()){
            saveStateArchivesList(position);
            openDiretory(archive.getFile());
        }else if(archive.isFile()){
            touchFile(position, archive.getFile());
        }
    }

    @Override
    public void onArchiveIconClick(Archive archive, int position){}


    @Override
    public void onArchiveSelected(List<String> listSelected){
        updateButtonSelectedItens(listSelected.size(), viewModel.getMaxSelectFiles());
    }



    /**DirectoryAdapterListener**/

    @Override
    public void onClickDirectory(Directory directory){
        if(viewModel.isLoadingDiretory()) return;
        openDiretory(directory.getFile());
    }



    /**Inits**/

    @Override
    protected void init(Bundle savedInstanceState){
        setSupportActionBar(binding.toolbar);
        setupViews();
        setupListeners();
        setupObservers();
        viewModel.notifyDirectory();
    }


    private void setupViews(){

        //recyclerArchive
        archiveAdapter = new ArchiveAdapter(this);
        binding.recyclerArchive.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerArchive.setAdapter(archiveAdapter);

        //recyclerDiretory
        directoryAdapter = new DirectoryAdapter(this);
        binding.recyclerDirectory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerDirectory.setAdapter(directoryAdapter);

        //ButtonSelect
        binding.buttonSelect.setText(archiveAdapter.getCountSelectedFiles() +"/"+viewModel.getMaxSelectFiles());
        if(viewModel.getMaxSelectFiles() > 1)
            binding.buttonSelect.setVisibility(View.VISIBLE);
        else 
            binding.buttonSelect.setVisibility(View.GONE);

    }


    private void setupListeners(){

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

                @Override
                public void onRefresh(){
                    viewModel.notifyDirectory();
                }

            });

        runnableShowProgress = new Runnable(){

            @Override
            public void run(){
                binding.swipeRefresh.setRefreshing(true);
                binding.progressBar.setVisibility(View.VISIBLE);
            }

        };

        binding.buttonSelect.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    if(archiveAdapter == null || archiveAdapter.getCountSelectedFiles() < 1) return;
                    setResult(Activity.RESULT_OK, new Intent().putStringArrayListExtra("Archives", archiveAdapter.getListFilesSelected()));
                    finish();
                }

            });

    }


    private void setupObservers(){

        //Prepare
        viewModel.getLiveDataPrepare().observe(this, new Observer<Void>(){

                @Override
                public void onChanged(Void voidValue){
                    progressStart(300);
                    updateToolbarIcons();
                    updateToolbarTexts(null);
                }
            });

        //Complete
        viewModel.getLiveDataCompleteArchives().observe(this, new Observer<List<Archive>>(){

                @Override
                public void onChanged(List<Archive> listArchive){
                    archiveAdapter.notifyList(listArchive);
                    binding.recyclerArchive.scrollToPosition(0);
                    progressCancel();
                    updateToolbarIcons();
                    updateToolbarTexts(listArchive);
                    checkEmptyListArchives(listArchive);

                }
            });

        //Progress
        viewModel.getLiveDataProgressValues().observe(this, new Observer<Integer[]>(){

                @Override
                public void onChanged(Integer[] progress){
                    final int progressCurrent = progress [0];
                    final int progressMax = progress [1];
                    progressUpdate(progressCurrent, progressMax);

                }

            });

        //Failure
        viewModel.getLiveDataFailureException().observe(this, new Observer<Exception>(){

                @Override
                public void onChanged(Exception exception){
                    progressCancel();
                    updateToolbarIcons();
                    checkEmptyListArchives(null);
                    toast(exception.toString());
                }

            });

        //Directory
        viewModel.getLiveDataListDirectory().observe(this, new Observer<List<Directory>>(){

                @Override
                public void onChanged(List<Directory> listDirectory){
                    directoryAdapter.notifyList(listDirectory);
                    binding.recyclerDirectory.scrollToPosition(listDirectory.size() - 1);
                }

            });

        //State   
        viewModel.getLiveDataStateListArchives().observe(this, new Observer<StateListArchives>(){

                @Override
                public void onChanged(StateListArchives state){
                    if(state == null) return;
                    binding.recyclerArchive.getLayoutManager().onRestoreInstanceState(state.getState());
                    archiveAdapter.animateIconPosition(state.getPosition());
                }

            });

    }

    /**Ações**/

    private boolean returnDiretory(){
        if(viewModel.isLoadingDiretory()) return false;
        return viewModel.returnDiretory();
    }


    private void openDiretory(File folder){
        viewModel.loadDiretory(folder);
    }


    private void touchFile(int position, File file){
        if(viewModel.getMaxSelectFiles() > 1)
        //Multi seleção
            if(archiveAdapter.isSelected(position)) archiveAdapter.unSelectItem(position);
            else{
                if(archiveAdapter.getCountSelectedFiles() >= viewModel.getMaxSelectFiles()) return;
                archiveAdapter.selectItem(position); 
             }
        else{
            //Seleção unica
            archiveAdapter.selectItem(position);
            setResult(Activity.RESULT_OK, new Intent().putStringArrayListExtra("Archives", archiveAdapter.getListFilesSelected()));
        } 

    }


    private void saveStateArchivesList(int position){
        final RecyclerView.LayoutManager lm = binding.recyclerArchive.getLayoutManager();
        if(lm == null) return;
        viewModel.saveStateArchives(position, lm.onSaveInstanceState());
    }


    /**Atualizar estados**/

    private void progressUpdate(final int progress, final int max){
        binding.progressBar.setMax(max);
        binding.progressBar.setProgress(progress);
    }

    private void progressStart(int delay){
        binding.progressBar.setProgress(0);
        binding.progressBar.postDelayed(runnableShowProgress, delay);
    }


    private void progressCancel(){
        if(runnableShowProgress == null) return;
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.swipeRefresh.setRefreshing(false);
        binding.progressBar.removeCallbacks(runnableShowProgress);
    }


    private void updateToolbarTexts(List<Archive> archive){
        binding.toolbar.setTitle(getString(R.string.toolbar_title));
        if(archive == null) {
            binding.toolbar.setSubtitle(getString(R.string.loading));
            return;
        }
        final int countFiles = Archive.countFiles(archive);
        final int countFolders = Archive.countFolders(archive);
        String file = (countFiles > 1 ? getString(R.string.files) : getString(R.string.file)).toLowerCase();
        String folder = (countFolders > 1 ? getString(R.string.folders) : getString(R.string.folder)).toLowerCase();
        binding.toolbar.setSubtitle(StringUtil.longToString(countFolders) + " " + folder + ", " + StringUtil.longToString(countFiles) + " " + file);
    }


    private void updateToolbarIcons(){
        if(menuHidden == null || menuClose == null) return;
        int alpha = viewModel.isLoadingDiretory() ? 150 : 255;
        menuHidden.getIcon().setAlpha(alpha);
        menuClose.getIcon().setAlpha(alpha);
        if(viewModel.isShowHiddenArchives()){
            menuHidden.setIcon(R.drawable.ic_eye);
            menuHidden.setTitle(R.string.gone_hidden_files);
        }else{
            menuHidden.setIcon(R.drawable.ic_eye_off);
            menuHidden.setTitle(R.string.show_hidden_files);
        }
    }


    private void checkEmptyListArchives(List<Archive> list){
        if(list == null || list.size() <= 0){
            binding.recyclerArchive.setVisibility(View.GONE);
            binding.viewNoItem.setVisibility(View.VISIBLE);
        }else{
            binding.recyclerArchive.setVisibility(View.VISIBLE);
            binding.viewNoItem.setVisibility(View.GONE);
        }
    }


    private void updateButtonSelectedItens(int itensSelected, int itensMax){
        if(itensSelected == itensMax) binding.buttonSelect.setIcon(ResourceUtil.getDrawable(this, R.drawable.ic_check));
        else binding.buttonSelect.setIcon(null);
        binding.buttonSelect.setText(itensSelected + "/" + itensMax);
    }


}
