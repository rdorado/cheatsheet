#include <iostream>
#include <fstream>
#include <list>

#include <dirent.h>

using namespace std;


void textFileToParagraphs(const string inputfile) {
  cout << inputfile << endl;
  string line;
  string paragraph = "";
  ifstream myfile (inputfile);
  if (myfile.is_open())
  {
  cout << "-->" << endl;
    while ( getline (myfile,line) )
    {
       if (line != "") {
          paragraph = paragraph + line + " ";
       }
       cout << line << '\n';
    }
  }
  myfile.close();
}

void processDirectory(const string directory, const string outdir) {

  DIR *dir;
  class dirent *ent;
  dir = opendir(directory.c_str());

  list<string> files;
  while ((ent = readdir(dir)) != NULL) {
    //cout << ent->d_type << " " << DT_REG << endl;
    if (ent->d_type == DT_REG) {
      const string file_name = ent->d_name;
      const string full_file_name = directory + file_name;

      //cout << full_file_name << endl;
      files.push_front(full_file_name);
    }
  }
  closedir(dir);

  for (list<string>::iterator it=files.begin(); it!=files.end(); ++it) {
    cout << *it << endl;
    textFileToParagraphs(*it);
  }
        
}

int main(){

  processDirectory("/home/rdorado/project/cheatsheet/data/", "/home/rdorado/project/cheatsheet/output/");

  //textFileToParagraphs("/home/rdorado/project/cheatsheet/data/pg27438.txt");
  /*
  string line;
  ifstream myfile ("../data/pg27438.txt");
  if (myfile.is_open())
  {
    while ( getline (myfile,line) )
    {
       cout << line << '\n';
    }
  }*/

  return 0;
}
